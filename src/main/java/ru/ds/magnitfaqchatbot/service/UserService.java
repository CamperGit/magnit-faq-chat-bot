package ru.ds.magnitfaqchatbot.service;

import ru.ds.magnitfaqchatbot.entity.UserEntity;


public interface UserService {

    UserEntity save(UserEntity user);

    UserEntity getById(Long id);

    void deleteById(Long id);
}
