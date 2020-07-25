package dev.kuchynski.pomodo.core.api;

import dev.kuchynski.pomodo.core.api.model.UserTimerSettings;

import javax.annotation.Nullable;

/**
 * Service for Pomodoro timer settings.
 *
 * @author <a href="mailto:o.kuchynski.gmail.com">Aleh Kuchynski</a>
 * @since 1.0
 */
public interface TimerSettingsService {

    /**
     * Gets {@link UserTimerSettings} by e-mail or return default setup.
     *
     * @param email user's email
     */
    UserTimerSettings getSettings(@Nullable String email);

    /**
     * Save user's timer settings.
     *
     * @param userTimerSettings {@link UserTimerSettings}
     */
    void saveSettings(UserTimerSettings userTimerSettings);
}
