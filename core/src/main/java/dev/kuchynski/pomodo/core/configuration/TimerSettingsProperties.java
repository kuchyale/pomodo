package dev.kuchynski.pomodo.core.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Default configuration properties of the Pomodoro timer.
 *
 * @author <a href="mailto:o.kuchynski@gmail.com">Aleh Kuchynski</a>
 * @since 1.0
 */
@Data
@Validated
@ConfigurationProperties("pomodo.timer-settings")
public class TimerSettingsProperties {

    private boolean autoStartNextRound;

    private int workPeriodTime;

    private int longBrakeTime;

    private int shortBrakeTime;

    private int periodsBeforeLongBreak;
}
