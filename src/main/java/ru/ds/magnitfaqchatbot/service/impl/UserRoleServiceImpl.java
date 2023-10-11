package ru.ds.magnitfaqchatbot.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.ds.magnitfaqchatbot.entity.UserRoleEntity;
import ru.ds.magnitfaqchatbot.repository.UserRoleRepository;
import ru.ds.magnitfaqchatbot.service.UserRoleService;

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
    public void deleteById(Long id) {
        userRoleRepository.deleteById(id);
    }
}
