package ru.ds.magnitfaqchatbotauthserver.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.ds.magnitfaqchatbotauthserver.entity.UserRoleEntity;
import ru.ds.magnitfaqchatbotauthserver.repository.UserRoleRepository;
import ru.ds.magnitfaqchatbotauthserver.service.UserRoleService;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserRoleServiceImpl implements UserRoleService {

    UserRoleRepository userRoleRepository;

    @Override
    public UserRoleEntity save(UserRoleEntity userRole) {
        return userRoleRepository.save(userRole);
    }

    @Override
    public UserRoleEntity getById(Long id) {
        return userRoleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Not found user role for id = %d", id)));
    }

    @Override
    public UserRoleEntity findRoleByName(String role) {
        return userRoleRepository.findByTitle(role)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Not found user role for title = %s", role)));
    }

    @Override
    public void deleteById(Long id) {
        userRoleRepository.deleteById(id);
    }
}
