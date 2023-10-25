package ru.ds.magnitfaqchatbotauthserver.service;


import ru.ds.magnitfaqchatbotauthserver.entity.UserRoleEntity;

public interface UserRoleService {

    UserRoleEntity save(UserRoleEntity userRole);

    UserRoleEntity getById(Long id);

    UserRoleEntity findRoleByName(String role);

    void deleteById(Long id);
}
