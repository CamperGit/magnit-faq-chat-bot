package ru.ds.magnitfaqchatbot.bot.handler.command;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ds.magnitfaqchatbot.bot.MagnitFaqChatBot;
import ru.ds.magnitfaqchatbot.service.LocaleMessageSource;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DefaultCommandHandler implements BotCommandHandler {

    static String UNKNOWN_COMMAND_MESSAGE_SOURCE = "main.unknown-non-command-message";

    LocaleMessageSource localeMessageSource;

    @Override
    public void handle(MagnitFaqChatBot bot, Update update) throws TelegramApiException {
        String chatId = update.getMessage().getChatId().toString();
        bot.execute(SendMessage.builder()
                .chatId(chatId)
                .text(localeMessageSource.getMessage(UNKNOWN_COMMAND_MESSAGE_SOURCE))
                .build());
    }

    @Override
    public boolean isApplicable(String message) {
        return false;
    }
}
