package dev.kuchynski.pomodo.web;

import dev.kuchynski.pomodo.core.configuration.TimerSettingsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring Boot application starter.
 *
 * @author <a href="mailto:o.kuchynski@gmail.com">Aleh Kuchynski</a>
 * @since 1.0
 */
@SpringBootApplication
@ComponentScan(basePackages = {"dev.kuchynski.pomodo"})
@ConfigurationPropertiesScan("dev.kuchynski.pomodo")
@EnableConfigurationProperties(TimerSettingsProperties.class)
public class PomodoApplication {

	/**
	 * Application entry point.
	 *
	 * @param args application arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(PomodoApplication.class, args);
	}

}
