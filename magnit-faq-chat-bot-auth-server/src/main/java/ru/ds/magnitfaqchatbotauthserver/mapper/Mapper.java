package ru.ds.magnitfaqchatbotauthserver.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import ru.ds.magnitfaqchatbotauthserver.dto.UserDto;
import ru.ds.magnitfaqchatbotauthserver.dto.UserRoleDto;
import ru.ds.magnitfaqchatbotauthserver.entity.UserEntity;
import ru.ds.magnitfaqchatbotauthserver.entity.UserRoleEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class Mapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.getConverterFactory().registerConverter(new PassThroughConverter(LocalDateTime.class));
        factory.getConverterFactory().registerConverter(new PassThroughConverter(LocalDate.class));

        factory.classMap(UserRoleEntity.class, UserRoleDto.class)
                .byDefault()
                .register();

        factory.classMap(UserEntity.class, UserDto.class)
                .byDefault()
                .register();
    }
}

