package dev.kuchynski.pomodo.core;

import dev.kuchynski.pomodo.core.api.TimerSettingsService;
import dev.kuchynski.pomodo.core.api.model.UserTimerSettings;
import dev.kuchynski.pomodo.core.configuration.TimerSettingsProperties;
import dev.kuchynski.pomodo.core.repository.UserTimerSettingsRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties(TimerSettingsProperties.class)
public class TimerSettingsServiceImpl implements TimerSettingsService {

    @NonNull
    private final UserTimerSettingsRepository timerSettingsRepository;

    @NonNull
    private final TimerSettingsProperties timerSettingsProperties;

    @Override
    public UserTimerSettings getSettings(@Nullable String email) {
        return timerSettingsRepository.findByEmail(email).orElse(getDefaultTimerSettings());
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

    private UserTimerSettings getDefaultTimerSettings() {
        UserTimerSettings timerSettingsDto = new UserTimerSettings();
        timerSettingsDto.setAutoStartNextRound(timerSettingsProperties.isAutoStartNextRound());
        timerSettingsDto.setWorkPeriodTime(timerSettingsProperties.getWorkPeriodTime());
        timerSettingsDto.setLongBrakeTime(timerSettingsProperties.getLongBrakeTime());
        timerSettingsDto.setShortBrakeTime(timerSettingsProperties.getShortBrakeTime());
        timerSettingsDto.setPeriodsBeforeLongBreak(timerSettingsProperties.getPeriodsBeforeLongBreak());

        return timerSettingsDto;
    }
}
