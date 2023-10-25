package ru.ds.magnitfaqchatbotauthserver.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.ds.magnitfaqchatbotauthserver.entity.UserEntity;
import ru.ds.magnitfaqchatbotauthserver.repository.UserRepository;
import ru.ds.magnitfaqchatbotauthserver.service.UserRoleService;
import ru.ds.magnitfaqchatbotauthserver.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    UserRoleService userRoleService;

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
}
