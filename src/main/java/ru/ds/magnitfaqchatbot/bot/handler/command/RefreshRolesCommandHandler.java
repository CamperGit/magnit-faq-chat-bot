package ru.ds.magnitfaqchatbot.bot.handler.command;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ds.magnitfaqchatbot.bot.MagnitFaqChatBot;
import ru.ds.magnitfaqchatbot.dto.UserRoleDto;
import ru.ds.magnitfaqchatbot.entity.UserEntity;
import ru.ds.magnitfaqchatbot.entity.UserRoleEntity;
import ru.ds.magnitfaqchatbot.mapper.Mapper;
import ru.ds.magnitfaqchatbot.model.auth.AuthUserRole;
import ru.ds.magnitfaqchatbot.service.AuthService;
import ru.ds.magnitfaqchatbot.service.LocaleMessageSource;
import ru.ds.magnitfaqchatbot.service.UserRoleService;
import ru.ds.magnitfaqchatbot.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Обработчик команды обновления ролей пользователя
 */
@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RefreshRolesCommandHandler implements BotCommandHandler {

    LocaleMessageSource localeMessageSource;

    AuthService authService;

    UserService userService;

    UserRoleService userRoleService;

    static String REFRESH_ROLES_SUCCESS_RESOURCE = "user.refresh-roles.success";
    static String REFRESH_ROLES_ERROR_RESOURCE = "user.refresh-roles.error";

    @Override
    public void handle(MagnitFaqChatBot bot, Update update) throws TelegramApiException {
        Message message = update.getMessage();
        String chatId = message.getChatId().toString();
        Long id = message.getFrom().getId();
        try {
            List<AuthUserRole> userRolesByTelegramId = authService.getUserRolesByTelegramId(id);
            List<UserRoleEntity> telegramUserRoles = userRoleService.findRolesByNames(userRolesByTelegramId.stream().map(AuthUserRole::getTitle).collect(Collectors.toList()));

            UserEntity user = userService.getByTelegramId(id);
            user.setRoles(telegramUserRoles);
            userService.save(user);

            SendMessage botMessage = new SendMessage(chatId, wrapInBold(localeMessageSource.getMessage(REFRESH_ROLES_SUCCESS_RESOURCE)));
            botMessage.enableHtml(true);
            bot.execute(botMessage);
        } catch (Exception e) {
            log.error("Unexpected error while trying to get user roles", e);
            SendMessage botMessage = new SendMessage(chatId, wrapInBold(localeMessageSource.getMessage(REFRESH_ROLES_ERROR_RESOURCE)));
            botMessage.enableHtml(true);
            bot.execute(botMessage);
        }
    }

    @Override
    public boolean isApplicable(String message) {
        return message.equals(CommandValue.REFRESH_ROLES.getValue());
    }
}
