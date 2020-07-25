package dev.kuchynski.pomodo.web.dto;

import lombok.Data;

/**
 * DTO object that represents Pomodoro timer settings.
 *
 * @author <a href="mailto:o.kuchynski@gmail.com">Aleh Kuchynski</a>
 * @since 1.0
 */
@Data
public class TimerSettingsDto {
    private String email;
    private Integer workPeriodTime;
    private Integer shortBrakeTime;
    private Integer longBrakeTime;
    private Integer periodsBeforeLongBreak;
    private Boolean autoStartNextRound;
}
