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
import ru.ds.magnitfaqchatbot.model.role.RolePermission;

import javax.persistence.AttributeConverter;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RolePermissionJsonConverter implements AttributeConverter<RolePermission, String> {

    ObjectMapper objectMapper;

    public RolePermissionJsonConverter() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.objectMapper = mapper;
    }

    @Override
    public String convertToDatabaseColumn(RolePermission rolePermissions) {
        try {
            return objectMapper.writeValueAsString(rolePermissions);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Couldn't convert object to json", e);
        }
    }

    @Override
    public RolePermission convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, RolePermission.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Couldn't read json object", e);
        }
    }
}
