package ru.ds.magnitfaqchatbot.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.ds.magnitfaqchatbot.entity.UserEntity;
import ru.ds.magnitfaqchatbot.model.auth.AuthUser;
import ru.ds.magnitfaqchatbot.model.user.SearchSettings;
import ru.ds.magnitfaqchatbot.model.user.UserSettings;
import ru.ds.magnitfaqchatbot.repository.UserRepository;
import ru.ds.magnitfaqchatbot.service.AuthService;
import ru.ds.magnitfaqchatbot.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    AuthService authService;

    UserRepository userRepository;

    @Override
    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public UserEntity getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Not found user for id = %d", id)));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public UserEntity getByTelegramId(Long telegramId) {
        return Optional.ofNullable(userRepository.findByTelegramId(telegramId))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Not found user for telegram id = %s", telegramId)));
    }

    public UserEntity getByTelegramIdOrCreate(Long telegramId, String username) {
        return Optional.ofNullable(userRepository.findByTelegramId(telegramId))
                .orElseGet(() -> createUserEntityByTelegramId(telegramId, username));
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity synchronizeUserWithAuthServer(UserEntity user) {
        AuthUser authUser = authService.getUserByTelegramId(user.getTelegramId());
        user.setFullName(authUser.getFullName());
        user.setDepartment(authUser.getDepartment());
        user.setRoles(authService.convertUserAuthRolesToTelegramRoles(authUser));
        return save(user);
    }

    private UserEntity createUserEntityByTelegramId(Long telegramId, String username) {
        UserEntity userEntity = new UserEntity();
        userEntity.setTelegramId(telegramId);
        userEntity.setUsername(username);
        userEntity.setSettings(UserSettings.builder()
                        .searchSettings(SearchSettings.builder()
                                .faqPageSize(5)
                                .build())
                .build());
        userEntity.setRoles(Collections.emptyList());
        return save(userEntity);
    }
}
