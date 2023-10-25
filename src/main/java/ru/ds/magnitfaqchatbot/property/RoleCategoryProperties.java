package ru.ds.magnitfaqchatbot.property;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "category")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleCategoryProperties {

    Map<String, List<String>> mapping = new HashMap();
}
