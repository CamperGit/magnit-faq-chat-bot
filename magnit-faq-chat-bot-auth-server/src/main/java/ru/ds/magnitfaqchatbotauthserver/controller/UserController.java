package ru.ds.magnitfaqchatbotauthserver.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ds.magnitfaqchatbotauthserver.dto.UserDto;
import ru.ds.magnitfaqchatbotauthserver.dto.UserRoleDto;
import ru.ds.magnitfaqchatbotauthserver.entity.UserEntity;
import ru.ds.magnitfaqchatbotauthserver.mapper.Mapper;
import ru.ds.magnitfaqchatbotauthserver.service.UserService;

import java.util.List;

/**
 * Контроллер пользователей
 */
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/v1/user")
public class UserController {

    UserService userService;

    Mapper mapper;

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto user) {
        return ResponseEntity.ok(mapper.map(userService.save(mapper.map(user, UserEntity.class)), UserDto.class));
    }


    @GetMapping("/telegram/{telegramId}")
    public ResponseEntity<List<UserRoleDto>> getUserRolesByTelegramId(@PathVariable Long telegramId) {
        UserEntity user = userService.getByTelegramId(telegramId);
        return ResponseEntity.ok(mapper.mapAsList(user.getRoles(), UserRoleDto.class));
    }
}
