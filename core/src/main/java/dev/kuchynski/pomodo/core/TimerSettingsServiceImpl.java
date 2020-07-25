package dev.kuchynski.pomodo.core;

import dev.kuchynski.pomodo.core.api.TimerSettingsService;
import dev.kuchynski.pomodo.core.api.model.UserTimerSettings;
import dev.kuchynski.pomodo.core.repository.UserTimerSettingsRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Nullable;

/**
 * Implementation of the {@link TimerSettingsService}.
 *
 * @author <a href="mailto:o.kuchynski@gmail.com">Aleh Kuchynski</a>
 * @see TimerSettingsService
 * @since 1.0
 */
@Service
@AllArgsConstructor
public class TimerSettingsServiceImpl implements TimerSettingsService {

    @NonNull
    private final UserTimerSettingsRepository timerSettingsRepository;

    @NonNull
    private final UserTimerSettings defaultUserTimerSettings;

    @Override
    public UserTimerSettings getSettings(@Nullable String email) {
        return timerSettingsRepository.findByEmail(email).orElse(defaultUserTimerSettings);
    }

    @Override
    public void saveSettings(UserTimerSettings userTimerSettings) {
        Assert.notNull(userTimerSettings, "userTimerSettings must not be null");

        UserTimerSettings dbTimerSettings = getSettings(userTimerSettings.getEmail());
        dbTimerSettings.setEmail(userTimerSettings.getEmail());
        dbTimerSettings.setAutoStartNextRound(userTimerSettings.getAutoStartNextRound());
        dbTimerSettings.setWorkPeriodTime(userTimerSettings.getWorkPeriodTime());
        dbTimerSettings.setShortBrakeTime(userTimerSettings.getShortBrakeTime());
        dbTimerSettings.setLongBrakeTime(userTimerSettings.getLongBrakeTime());
        dbTimerSettings.setPeriodsBeforeLongBreak(userTimerSettings.getPeriodsBeforeLongBreak());

        timerSettingsRepository.save(userTimerSettings);
    }
}
