package ru.ds.magnitfaqchatbot.service;

import org.springframework.transaction.annotation.Transactional;
import ru.ds.magnitfaqchatbot.entity.UserEntity;


public interface UserService {

    UserEntity save(UserEntity user);

    @Transactional
    UserEntity getById(Long id);

    void deleteById(Long id);

    @Transactional
    UserEntity getByTelegramId(String telegramId);
}
