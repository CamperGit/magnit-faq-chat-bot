package ru.ds.magnitfaqchatbot.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.ds.magnitfaqchatbot.entity.UserEntity;
import ru.ds.magnitfaqchatbot.entity.UserRoleEntity;
import ru.ds.magnitfaqchatbot.model.role.UserRole;
import ru.ds.magnitfaqchatbot.model.user.SearchSettings;
import ru.ds.magnitfaqchatbot.model.user.UserSettings;
import ru.ds.magnitfaqchatbot.repository.UserRepository;
import ru.ds.magnitfaqchatbot.service.UserRoleService;
import ru.ds.magnitfaqchatbot.service.UserService;

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

    public UserEntity getByTelegramId(String telegramId) {
        return Optional.ofNullable(userRepository.findByTelegramId(telegramId))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Not found user for telegram id = %s", telegramId)));
    }

    public UserEntity getByTelegramIdOrCreate(String telegramId) {
        return Optional.ofNullable(userRepository.findByTelegramId(telegramId))
                .orElseGet(() -> mockNotFoundUserEntityByTelegramId(telegramId));
    }

    private UserEntity mockNotFoundUserEntityByTelegramId(String telegramId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setTelegramId(telegramId);
        userEntity.setFullName("Mocked user full name");
        userEntity.setDepartment("Mocked user department");
        userEntity.setSettings(UserSettings.builder()
                        .searchSettings(SearchSettings.builder()
                                .faqPageSize(5)
                                .build())
                .build());
        userEntity.setRoles(Collections.singletonList(getRandomRoleEntity()));
        return save(userEntity);
    }

    private UserRoleEntity getRandomRoleEntity() {
        List<String> roles = Stream.of(UserRole.ROLE_PERFUMERY, UserRole.ROLE_ALCOHOL, UserRole.ROLE_TOBACCO, UserRole.ROLE_TEXTILE)
                .map(UserRole::name)
                .collect(Collectors.toList());
        Random random = new Random();
        String roleTitle = roles.get(random.nextInt(roles.size()));
        return userRoleService.findRoleByName(roleTitle);
    }
}
