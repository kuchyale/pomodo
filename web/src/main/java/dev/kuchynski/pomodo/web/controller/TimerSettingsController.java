package dev.kuchynski.pomodo.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kuchynski.pomodo.core.api.TimerSettingsService;
import dev.kuchynski.pomodo.web.dto.TimerSettingsDto;
import dev.kuchynski.pomodo.web.mapper.TimerSettingsMapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Nullable;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller for operations with timer settings.
 *
 * @author <a href="mailto:o.kuchynski@gmail.com">Aleh Kuchynski</a>
 * @since 1.0
 */
@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("/timer-settings")
public class TimerSettingsController {

    private static final int ONE_WEEK_IN_SECONDS = 3600 * 24 * 7;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @NonNull
    private final TimerSettingsService timerSettingsService;

    @NonNull
    private final TimerSettingsMapper timerSettingsMapper;

    /**
     * Controller for saving timer's settings.
     *
     * @param authToken        {@link OAuth2AuthenticationToken}
     * @param timerSettingsDto {@link TimerSettingsDto}
     * @param result           {@link BindingResult}
     * @param model            view model
     * @param response         {@link HttpServletResponse}
     */
    @PostMapping
    public String saveTimerSettings(@Nullable OAuth2AuthenticationToken authToken,
                                    @ModelAttribute("timerSettings") TimerSettingsDto timerSettingsDto,
                                    BindingResult result, Model model, HttpServletResponse response)
            throws JsonProcessingException {
        Assert.notNull(timerSettingsDto, "timerSettingsDto not be null");

        // todo add validations and error processing

        if (authToken != null) {
            timerSettingsDto.setEmail(authToken.getPrincipal().getAttribute("email"));
            timerSettingsService.saveSettings(timerSettingsMapper.toTimerSettings(timerSettingsDto));
        }

        // todo add disclaimer about using cookies to web page
        // also save settings to cookie
        byte[] timerSettingsJson = objectMapper.writeValueAsBytes(timerSettingsDto);
        Cookie timerSettingsCookie = new Cookie("timerSettings", Base64Utils.encodeToString(timerSettingsJson));
        timerSettingsCookie.setMaxAge(ONE_WEEK_IN_SECONDS); // set expire time to one week
        response.addCookie(timerSettingsCookie);

        model.addAttribute("timerSettings", timerSettingsDto);

        return "redirect:/";
    }
}
