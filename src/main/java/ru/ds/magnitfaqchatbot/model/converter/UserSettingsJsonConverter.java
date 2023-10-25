package ru.ds.magnitfaqchatbot.model.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.ds.magnitfaqchatbot.model.user.UserSettings;

import javax.persistence.AttributeConverter;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserSettingsJsonConverter implements AttributeConverter<UserSettings, String> {

    ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(UserSettings attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Couldn't convert object to json", e);
        }
    }

    @Override
    public UserSettings convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, UserSettings.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Couldn't read json object", e);
        }
    }
}
