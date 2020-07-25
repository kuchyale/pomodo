package dev.kuchynski.pomodo.core.repository;

import dev.kuchynski.pomodo.core.api.model.UserTimerSettings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Repository for {@link UserTimerSettings}.
 *
 * @author <a href="mailto:o.kuchynski.gmail.com">Aleh Kuchynski</a>
 * @since 1.0
 */
@Repository
public interface UserTimerSettingsRepository extends CrudRepository<UserTimerSettings, Long> {

    /**
     * Finds {@link UserTimerSettings} by e-mail.
     *
     * @param email email
     * @return {@link UserTimerSettings} if was found, {@code null} - otherwise
     */
    @Nullable
    Optional<UserTimerSettings> findByEmail(@Nullable String email);
}
