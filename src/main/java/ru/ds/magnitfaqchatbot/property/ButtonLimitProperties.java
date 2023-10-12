package ru.ds.magnitfaqchatbot.property;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

@Data
@Validated
@Configuration
@ConfigurationProperties("button.limit")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ButtonLimitProperties {

    @Positive
    @NotNull(message = "Examples button limit property not found")
    Integer examples;

    @Positive
    @NotNull(message = "Algorithms button limit property not found")
    Integer algorithms;
}
