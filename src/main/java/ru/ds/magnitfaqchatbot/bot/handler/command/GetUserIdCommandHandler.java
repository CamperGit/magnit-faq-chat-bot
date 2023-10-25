package ru.ds.magnitfaqchatbot.bot.handler.command;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ds.magnitfaqchatbot.bot.MagnitFaqChatBot;
import ru.ds.magnitfaqchatbot.service.LocaleMessageSource;

/**
 * Обработчик команды получения идентификатора пользователя в телеграмм
 */
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetUserIdCommandHandler implements BotCommandHandler {

    LocaleMessageSource localeMessageSource;

    static String GET_USER_ID_RESOURCE = "user.get-id";

    @Override
    public void handle(MagnitFaqChatBot bot, Update update) throws TelegramApiException {
        Message message = update.getMessage();
        String chatId = message.getChatId().toString();
        Long id = message.getFrom().getId();
        SendMessage botMessage = new SendMessage(chatId, String.format("%s: \"%d\"", wrapInBold(localeMessageSource.getMessage(GET_USER_ID_RESOURCE)), id));
        botMessage.enableHtml(true);
        bot.execute(botMessage);
    }

    @Override
    public boolean isApplicable(String message) {
        return message.equals(CommandValue.GET_USER_ID.getValue());
    }
}
