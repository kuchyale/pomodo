package dev.kuchynski.pomodo.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot application starter.
 *
 * @author <a href="mailto:o.kuchynski@gmail.com">Aleh Kuchynski</a>
 * @since 1.0
 */
@SpringBootApplication
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
