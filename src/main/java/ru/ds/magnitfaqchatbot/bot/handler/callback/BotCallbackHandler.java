package ru.ds.magnitfaqchatbot.bot.handler.callback;

import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ds.magnitfaqchatbot.bot.MagnitFaqChatBot;

public interface BotCallbackHandler {

    @Transactional
    void handle(MagnitFaqChatBot bot, Update update) throws TelegramApiException;

    boolean isApplicable(String callback);
}
