package ru.ds.magnitfaqchatbot.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import ru.ds.magnitfaqchatbot.dto.*;
import ru.ds.magnitfaqchatbot.entity.*;

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

        factory.classMap(AlgorithmEntity.class, AlgorithmDto.class)
                .byDefault()
                .register();

        factory.classMap(CategoryEntity.class, CategoryDto.class)
                .byDefault()
                .register();

        factory.classMap(ExampleEntity.class, ExampleDto.class)
                .byDefault()
                .register();

        factory.classMap(FaqEntity.class, FaqDto.class)
                .byDefault()
                .register();
    }
}

