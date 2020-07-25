package dev.kuchynski.pomodo.web.mapper;

import dev.kuchynski.pomodo.core.api.model.UserTimerSettings;
import dev.kuchynski.pomodo.web.controller.TimerSettingsController;
import dev.kuchynski.pomodo.web.dto.TimerSettingsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for {@link TimerSettingsController}.
 *
 * @author <a href="mailto:o.kuchynski@gmail.com">Aleh Kuchynski</a>
 * @see TimerSettingsController
 * @since 1.0
 */
@Mapper
public interface TimerSettingsMapper {

    /**
     * Maps {@link TimerSettingsDto} to {@link UserTimerSettings}.
     *
     * @param input {@link TimerSettingsDto}
     * @return {@link UserTimerSettings}
     */
    @Mapping(target = "id", ignore = true)
    UserTimerSettings toTimerSettings(TimerSettingsDto input);

    /**
     * Maps {@link UserTimerSettings} to {@link TimerSettingsDto}.
     *
     * @param input {@link UserTimerSettings}
     * @return {@link TimerSettingsDto}
     */
    TimerSettingsDto toTimerSettingsDto(UserTimerSettings input);
}
