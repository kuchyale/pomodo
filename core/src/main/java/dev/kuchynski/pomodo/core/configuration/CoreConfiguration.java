package dev.kuchynski.pomodo.core.configuration;

import dev.kuchynski.pomodo.core.api.model.UserTimerSettings;
import dev.kuchynski.pomodo.core.repository.UserTimerSettingsRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
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
public class CoreConfiguration {
}
