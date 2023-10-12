package ru.ds.magnitfaqchatbot.model.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.ds.magnitfaqchatbot.model.role.RolePermissions;

import javax.persistence.AttributeConverter;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RolePermissionsJsonConverter implements AttributeConverter<RolePermissions, String> {

    ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(RolePermissions rolePermissions) {
        try {
            return objectMapper.writeValueAsString(rolePermissions);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Couldn't convert object to json", e);
        }
    }

    @Override
    public RolePermissions convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, RolePermissions.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Couldn't read json object", e);
        }
    }
}
