package dev.kuchynski.pomodo.web.controller;

import com.aaroncoplan.todoist.Todoist;
import com.aaroncoplan.todoist.TodoistException;
import com.aaroncoplan.todoist.model.Due;
import com.aaroncoplan.todoist.model.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kuchynski.pomodo.core.api.TimerSettingsService;
import dev.kuchynski.pomodo.web.dto.TimerSettingsDto;
import dev.kuchynski.pomodo.web.mapper.TimerSettingsMapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Nullable;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for dashboard page.
 *
 * @author <a href="mailto:o.kuchynski@gmail.com">Aleh Kuchynski</a>
 * @since 1.0
 */
@Slf4j
@AllArgsConstructor
@Controller
public class DashboardController {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String COOKIE_DEFAULT_VALUE = "DEFAULT";

    @NonNull
    private final OAuth2AuthorizedClientService authorizedClientService;

    @NonNull
    private final TimerSettingsService timerSettingsService;

    @NonNull
    private final TimerSettingsMapper timerSettingsMapper;

    /**
     * Return dashboard page.
     *
     * @param authToken           {@link OAuth2AuthenticationToken}
     * @param withOverdue         determines if overdue tasks should be included
     * @param timerSettingsCookie cookie that contains timer settings
     * @param session             HTTP session
     * @param model               view model
     * @return dashboard page
     */
    @GetMapping("/")
    public String getDashboard(@Nullable OAuth2AuthenticationToken authToken,
                               @Nullable @RequestParam("withOverdue") Boolean withOverdue,
                               @CookieValue(value = "timerSettings", defaultValue = COOKIE_DEFAULT_VALUE) String timerSettingsCookie,
                               @Nullable HttpSession session, Model model) throws IOException {
        List<Task> tasks = null;
        String email = null;
        if (authToken != null) {
            OAuth2User principal = authToken.getPrincipal();
            OAuth2AuthorizedClient oAuth2AuthorizedClient = authorizedClientService.loadAuthorizedClient(
                    authToken.getAuthorizedClientRegistrationId(), principal.getName());
            if (oAuth2AuthorizedClient != null) {
                email = principal.getAttribute("email");

                Todoist todoist = new Todoist(oAuth2AuthorizedClient.getAccessToken().getTokenValue());
                try {
                    tasks = todoist.getActiveTasks().stream()
                            .filter(task -> filterTodayTask(task, withOverdue))
                            .collect(Collectors.toList());
                } catch (TodoistException e) {
                    throw new IllegalStateException("Error was occurred while trying to get Todoist active tasks", e);
                }
            } else if (session != null) {
                // clear security context and session if there is no access token
                SecurityContextHolder.clearContext();
                session.invalidate();
            }
        }

        if (CollectionUtils.isNotEmpty(tasks)) {
            model.addAttribute("tasks", tasks);
        }

        TimerSettingsDto timerSettingsDto;
        if (StringUtils.isBlank(email) && !timerSettingsCookie.equals(COOKIE_DEFAULT_VALUE)) {
            byte[] decodedTimerSettingsCookie = Base64Utils.decodeFromString(timerSettingsCookie);
            timerSettingsDto = objectMapper.readValue(decodedTimerSettingsCookie, TimerSettingsDto.class);
        } else {
            timerSettingsDto = timerSettingsMapper.toTimerSettingsDto(timerSettingsService.getSettings(email));
        }
        model.addAttribute("timerSettings", timerSettingsDto);

        return "dashboard";
    }

    /**
     * Filters task with today's due date.
     *
     * @param task        {@link Task}
     * @param withOverdue decides if overdue tasks should be filtered
     * @return {@code true} if task is with today's due date, {@code false} - otherwise
     */
    private boolean filterTodayTask(Task task, @Nullable Boolean withOverdue) {
        Assert.notNull(task, "task must not be null");

        Due due = task.due;
        if (due == null) {
            return false;
        }

        LocalDate dueDate = LocalDate.parse(due.date);
        LocalDate today = LocalDate.now();
        boolean isDueToday = dueDate.isEqual(today);
        return Boolean.TRUE.equals(withOverdue) ? (dueDate.isBefore(today) || isDueToday) : isDueToday;
    }
}
