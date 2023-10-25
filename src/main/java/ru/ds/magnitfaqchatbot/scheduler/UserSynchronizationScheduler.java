package ru.ds.magnitfaqchatbot.scheduler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.ds.magnitfaqchatbot.entity.UserEntity;
import ru.ds.magnitfaqchatbot.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserSynchronizationScheduler {

    UserService userService;

    /**
     * Шедулер для синхронизации пользователей с auth-сервером
     */
    @Scheduled(cron = "${scheduler.user-synchronization}")
    public void syncPersons() {
        log.info("Started synchronizing users with auth server");
        List<UserEntity> users = userService.findAll().stream().filter(user -> user.getTelegramId() != null).collect(Collectors.toList());
        for (UserEntity userEntity : users) {
            userService.synchronizeUserWithAuthServer(userEntity);
        }
        log.info("Finished synchronizing {} users with auth server", users.size());
    }
}
