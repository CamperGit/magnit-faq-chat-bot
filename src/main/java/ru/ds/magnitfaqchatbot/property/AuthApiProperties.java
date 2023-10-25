package ru.ds.magnitfaqchatbot.property;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "api.auth")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthApiProperties {

    @NotEmpty(message = "Get user by telegram id resource not found")
    String getUserByTelegramIdResource;
}
