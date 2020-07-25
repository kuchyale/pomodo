package dev.kuchynski.pomodo.core.repository;

import dev.kuchynski.pomodo.core.api.model.UserTimerSettings;
import dev.kuchynski.pomodo.core.configuration.CoreConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import javax.inject.Inject;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for {@link UserTimerSettingsRepository}.
 *
 * @author <a href="mailto:o.kuchynski@gmail.com">Aleh Kuchynski</a>
 * @since 1.0
 */
@ContextConfiguration(classes = CoreConfiguration.class)
@DataJpaTest
class UserTimerSettingsRepositoryTest {

    @Inject
    private UserTimerSettingsRepository timerSettingsRepository;

    /**
     * Test for {@link UserTimerSettingsRepository#findByEmail(String)}.
     */
    @Test
    void test_findByEmail() {
        UserTimerSettings timerSettings = new UserTimerSettings();
        timerSettings.setEmail("test@test.cz");
        timerSettings.setWorkPeriodTime(25);
        timerSettings.setShortBrakeTime(5);
        timerSettings.setLongBrakeTime(14);
        timerSettings.setAutoStartNextRound(true);
        timerSettings.setPeriodsBeforeLongBreak(3);
        timerSettingsRepository.save(timerSettings);

        UserTimerSettings foundTimerSettings = timerSettingsRepository.findByEmail(timerSettings.getEmail()).get();
        assertThat(foundTimerSettings).isNotNull();
        assertThat(foundTimerSettings.getEmail()).isEqualTo(timerSettings.getEmail());
        assertThat(foundTimerSettings.getWorkPeriodTime()).isEqualTo(timerSettings.getWorkPeriodTime());
        assertThat(foundTimerSettings.getShortBrakeTime()).isEqualTo(timerSettings.getShortBrakeTime());
        assertThat(foundTimerSettings.getLongBrakeTime()).isEqualTo(timerSettings.getLongBrakeTime());
        assertThat(foundTimerSettings.getAutoStartNextRound()).isTrue();
        assertThat(foundTimerSettings.getPeriodsBeforeLongBreak()).isEqualTo(timerSettings.getPeriodsBeforeLongBreak());

        Optional<UserTimerSettings> notFoundTimerSettings = timerSettingsRepository.findByEmail(null);
        assertThat(notFoundTimerSettings.isPresent()).isFalse();

        notFoundTimerSettings = timerSettingsRepository.findByEmail("aa@test.cz");
        assertThat(notFoundTimerSettings.isPresent()).isFalse();
    }
}