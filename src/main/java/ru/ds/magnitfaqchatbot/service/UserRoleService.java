package ru.ds.magnitfaqchatbot.service;

import ru.ds.magnitfaqchatbot.entity.UserRoleEntity;


public interface UserRoleService {

    UserRoleEntity save(UserRoleEntity userRole);

    UserRoleEntity getById(Long id);

    void deleteById(Long id);
}
