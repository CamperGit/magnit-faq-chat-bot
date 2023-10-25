package ru.ds.magnitfaqchatbot.service;

import ru.ds.magnitfaqchatbot.dto.UserRoleDto;
import ru.ds.magnitfaqchatbot.model.auth.AuthUserRole;

import java.util.List;

public interface AuthService {

    List<AuthUserRole> getUserRolesByTelegramId(Long telegramId);
}
