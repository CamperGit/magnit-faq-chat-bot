package ru.ds.magnitfaqchatbot.service;

import ru.ds.magnitfaqchatbot.entity.UserRoleEntity;
import ru.ds.magnitfaqchatbot.model.auth.AuthUser;

import java.util.List;

public interface AuthService {

    /**
     * Получение пользователя из сервера аутентификации по идентификатору телеграм
     *
     * @param telegramId - идентификатор телеграм
     * @return пользователь
     */
    AuthUser getUserByTelegramId(Long telegramId);

    /**
     * Конвертирует роли сервера аутентификации в роли телеграм бота
     *
     * @param user - пользователь
     * @return роли телеграм бота
     */
    List<UserRoleEntity> convertUserAuthRolesToTelegramRoles(AuthUser user);
}
