package ru.ds.magnitfaqchatbot.service;

import ru.ds.magnitfaqchatbot.entity.UserRoleEntity;
import ru.ds.magnitfaqchatbot.model.role.UserRole;


public interface UserRoleService {

    UserRoleEntity save(UserRoleEntity userRole);

    UserRoleEntity getById(Long id);

    UserRoleEntity findRoleByName(String role);

    void deleteById(Long id);
}
