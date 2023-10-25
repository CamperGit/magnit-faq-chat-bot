package ru.ds.magnitfaqchatbot.service;

import ru.ds.magnitfaqchatbot.entity.UserRoleEntity;
import ru.ds.magnitfaqchatbot.model.role.UserRole;

import java.util.List;


public interface UserRoleService {

    UserRoleEntity save(UserRoleEntity userRole);

    UserRoleEntity getById(Long id);

    UserRoleEntity findRoleByName(String role);

    List<UserRoleEntity> findRolesByNames(List<String> roles);

    void deleteById(Long id);
}
