package dev.kuchynski.pomodo.web.controller;

import com.aaroncoplan.todoist.Todoist;
import com.aaroncoplan.todoist.TodoistException;
import com.aaroncoplan.todoist.model.Due;
import com.aaroncoplan.todoist.model.Task;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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

    @NonNull
    private final OAuth2AuthorizedClientService authorizedClientService;

    /**
     * Return dashboard page.
     *
     * @param authToken   {@link OAuth2AuthenticationToken}
     * @param withOverdue determines if overdue tasks should be included
     * @return dashboard page with model data
     */
    @GetMapping("/")
    public ModelAndView getDashboard(@Nullable OAuth2AuthenticationToken authToken,
                                     @Nullable @RequestParam("withOverdue") Boolean withOverdue) {
        List<Task> tasks = null;
        if (authToken != null) {
            OAuth2AuthorizedClient oAuth2AuthorizedClient = authorizedClientService.loadAuthorizedClient(
                    authToken.getAuthorizedClientRegistrationId(), authToken.getPrincipal().getName());
            if (oAuth2AuthorizedClient != null) {
                Todoist todoist = new Todoist(oAuth2AuthorizedClient.getAccessToken().getTokenValue());
                try {
                    tasks = todoist.getActiveTasks().stream()
                            .filter(task -> filterTaskByDueDate(task, withOverdue))
                            .collect(Collectors.toList());
                } catch (TodoistException e) {
                    throw new IllegalStateException("Error was occurred while trying to get Todoist active tasks", e);
                }
            }
        }

        return new ModelAndView("dashboard", CollectionUtils.isEmpty(tasks) ? null : Map.of("tasks", tasks));
    }

    private boolean filterTaskByDueDate(Task task, @Nullable Boolean withOverdue) {
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
