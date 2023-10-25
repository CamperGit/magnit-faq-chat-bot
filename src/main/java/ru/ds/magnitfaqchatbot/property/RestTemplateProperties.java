package ru.ds.magnitfaqchatbot.property;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "rest.template")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestTemplateProperties {

    @NotNull(message = "Read timeout property not found")
    Long readTimeout;

    @NotBlank(message = "Auth user service name property not found")
    String authUserServiceName;
}
