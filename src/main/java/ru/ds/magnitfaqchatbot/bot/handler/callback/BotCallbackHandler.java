package ru.ds.magnitfaqchatbot.bot.handler.callback;

import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ds.magnitfaqchatbot.bot.MagnitFaqChatBot;
import ru.ds.magnitfaqchatbot.service.BotUtilService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface BotCallbackHandler extends BotUtilService {

    @Transactional
    void handle(MagnitFaqChatBot bot, Update update) throws TelegramApiException;

    boolean isApplicable(String callback);
}
