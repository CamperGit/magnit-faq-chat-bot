package ru.ds.magnitfaqchatbot.service.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import ru.ds.magnitfaqchatbot.entity.UserRoleEntity;
import ru.ds.magnitfaqchatbot.model.auth.AuthUser;
import ru.ds.magnitfaqchatbot.model.auth.AuthUserRole;
import ru.ds.magnitfaqchatbot.property.AuthApiProperties;
import ru.ds.magnitfaqchatbot.service.AuthService;
import ru.ds.magnitfaqchatbot.service.UserRoleService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ru.ds.magnitfaqchatbot.config.RestConfiguration.DEFAULT_AUTH_USER_REST_TEMPLATE_BEAN;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {

    RestTemplate restTemplate;
    AuthApiProperties authApiProperties;
    UserRoleService userRoleService;

    public AuthServiceImpl(@Qualifier(DEFAULT_AUTH_USER_REST_TEMPLATE_BEAN) RestTemplate restTemplate,
                           AuthApiProperties authApiProperties,
                           UserRoleService userRoleService) {
        this.restTemplate = restTemplate;
        this.authApiProperties = authApiProperties;
        this.userRoleService = userRoleService;
    }

    @Override
    public AuthUser getUserByTelegramId(Long telegramId) {
        ResponseEntity<AuthUser> response = restTemplate.exchange(
                getResource(authApiProperties.getGetUserByTelegramIdResource(), telegramId),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        return response.getBody();
    }

    @Override
    public List<UserRoleEntity> convertUserAuthRolesToTelegramRoles(AuthUser user) {
        if (user == null || CollectionUtils.isEmpty(user.getRoles())) {
            return Collections.emptyList();
        }
        return userRoleService.findRolesByNames(user.getRoles().stream().map(AuthUserRole::getTitle).collect(Collectors.toList()));
    }

    private String getResource(String resource, Object... args) {
        return String.format(resource, args);
    }
}
