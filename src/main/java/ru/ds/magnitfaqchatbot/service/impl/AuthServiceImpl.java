package ru.ds.magnitfaqchatbot.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.ds.magnitfaqchatbot.dto.UserRoleDto;
import ru.ds.magnitfaqchatbot.model.auth.AuthUserRole;
import ru.ds.magnitfaqchatbot.property.AuthApiProperties;
import ru.ds.magnitfaqchatbot.service.AuthService;

import java.util.List;

import static ru.ds.magnitfaqchatbot.config.RestConfiguration.DEFAULT_AUTH_USER_REST_TEMPLATE_BEAN;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {

    RestTemplate restTemplate;
    AuthApiProperties authApiProperties;

    public AuthServiceImpl(@Qualifier(DEFAULT_AUTH_USER_REST_TEMPLATE_BEAN) RestTemplate restTemplate,
                           AuthApiProperties authApiProperties) {
        this.restTemplate = restTemplate;
        this.authApiProperties = authApiProperties;
    }

    @Override
    public List<AuthUserRole> getUserRolesByTelegramId(Long telegramId) {
        ResponseEntity<List<AuthUserRole>> response = restTemplate.exchange(
                getResource(authApiProperties.getGetRolesByTelegramIdResource(), telegramId),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        return response.getBody();
    }

    private String getResource(String resource, Object... args) {
        return String.format(resource, args);
    }
}
