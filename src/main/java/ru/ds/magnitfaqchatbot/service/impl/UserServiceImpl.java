package ru.ds.magnitfaqchatbot.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.ds.magnitfaqchatbot.entity.UserEntity;
import ru.ds.magnitfaqchatbot.repository.UserRepository;
import ru.ds.magnitfaqchatbot.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

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

    public UserEntity getByTelegramId(String telegramId) {
        return Optional.ofNullable(userRepository.findByTelegramId(telegramId))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Not found user for telegram id = %s", telegramId)));
    }
}
