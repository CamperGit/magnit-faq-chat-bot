package ru.ds.magnitfaqchatbotauthserver.service;

import org.springframework.transaction.annotation.Transactional;
import ru.ds.magnitfaqchatbotauthserver.entity.UserEntity;


public interface UserService {

    UserEntity save(UserEntity user);

    @Transactional
    UserEntity getById(Long id);

    void deleteById(Long id);

    @Transactional
    UserEntity getByTelegramId(Long telegramId);
}
