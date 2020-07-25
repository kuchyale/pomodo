package dev.kuchynski.pomodo.core.api.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Contains Pomodoro timer settings for concrete user.
 *
 * @author <a href="mailto:o.kuchynski.gmail.com">Aleh Kuchynski</a>
 * @since 1.0
 */
@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "user_timer_settings")
public class UserTimerSettings {

    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    @Column(name = "timer_settings_id")
    private Integer id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "work_period_time")
    private Integer workPeriodTime;

    @Column(name = "short_brake_time")
    private Integer shortBrakeTime;

    @Column(name = "long_brake_time")
    private Integer longBrakeTime;

    @Column(name = "periods_bfr_long_break")
    private Integer periodsBeforeLongBreak;

    @Column(name = "auto_start_next_period")
    private Boolean autoStartNextRound;
}
