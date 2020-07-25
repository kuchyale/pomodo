package dev.kuchynski.pomodo.core.configuration;

import dev.kuchynski.pomodo.core.api.model.UserTimerSettings;
import dev.kuchynski.pomodo.core.repository.UserTimerSettingsRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configuration for core module.
 *
 * @author <a href="mailto:o.kuchynski@gmail.com">Aleh Kuchynski</a>
 * @since 1.0
 */
@Configuration
@EntityScan(basePackageClasses = UserTimerSettings.class)
@EnableJpaRepositories(basePackageClasses = UserTimerSettingsRepository.class)
@EnableConfigurationProperties(TimerSettingsProperties.class)
public class CoreConfiguration {

    /**
     * Gets {@link UserTimerSettings} with default settings (given from application properties).
     *
     * @param timerSettingsProperties {@link TimerSettingsProperties}
     * @return {@link UserTimerSettings} with default settings
     */
    @Bean
    public UserTimerSettings defaultTimerSettings(TimerSettingsProperties timerSettingsProperties) {
        UserTimerSettings timerSettingsDto = new UserTimerSettings();
        timerSettingsDto.setAutoStartNextRound(timerSettingsProperties.isAutoStartNextRound());
        timerSettingsDto.setWorkPeriodTime(timerSettingsProperties.getWorkPeriodTime());
        timerSettingsDto.setLongBrakeTime(timerSettingsProperties.getLongBrakeTime());
        timerSettingsDto.setShortBrakeTime(timerSettingsProperties.getShortBrakeTime());
        timerSettingsDto.setPeriodsBeforeLongBreak(timerSettingsProperties.getPeriodsBeforeLongBreak());

        return timerSettingsDto;
    }
}
