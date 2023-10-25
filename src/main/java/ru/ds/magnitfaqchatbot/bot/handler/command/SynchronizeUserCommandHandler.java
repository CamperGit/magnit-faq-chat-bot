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
import ru.ds.magnitfaqchatbot.entity.UserEntity;
import ru.ds.magnitfaqchatbot.entity.UserRoleEntity;
import ru.ds.magnitfaqchatbot.model.auth.AuthUser;
import ru.ds.magnitfaqchatbot.service.AuthService;
import ru.ds.magnitfaqchatbot.service.LocaleMessageSource;
import ru.ds.magnitfaqchatbot.service.UserService;

import java.util.List;

/**
 * Обработчик команды обновления ролей пользователя
 */
@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SynchronizeUserCommandHandler implements BotCommandHandler {

    LocaleMessageSource localeMessageSource;

    AuthService authService;

    UserService userService;

    static String SYNCHRONIZE_USER_SUCCESS_RESOURCE = "user.synchronize.success";
    static String SYNCHRONIZE_USER_ERROR_RESOURCE = "user.synchronize.error";

    @Override
    public void handle(MagnitFaqChatBot bot, Update update) throws TelegramApiException {
        Message message = update.getMessage();
        String chatId = message.getChatId().toString();
        Long id = message.getFrom().getId();
        try {
            UserEntity user = userService.getByTelegramId(id);
            userService.synchronizeUserWithAuthServer(user);

            SendMessage botMessage = new SendMessage(chatId, wrapInBold(localeMessageSource.getMessage(SYNCHRONIZE_USER_SUCCESS_RESOURCE)));
            botMessage.enableHtml(true);
            bot.execute(botMessage);
        } catch (Exception e) {
            log.error("Unexpected error while trying to synchronize user with auth server", e);
            SendMessage botMessage = new SendMessage(chatId, wrapInBold(localeMessageSource.getMessage(SYNCHRONIZE_USER_ERROR_RESOURCE)));
            botMessage.enableHtml(true);
            bot.execute(botMessage);
        }
    }

    @Override
    public boolean isApplicable(String message) {
        return message.equals(CommandValue.SYNCHRONIZE_USER.getValue());
    }
}
