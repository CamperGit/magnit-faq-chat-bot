package ru.ds.magnitfaqchatbot.bot.handler.callback;

import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ds.magnitfaqchatbot.bot.MagnitFaqChatBot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface BotCallbackHandler {

    @Transactional
    void handle(MagnitFaqChatBot bot, Update update) throws TelegramApiException;

    boolean isApplicable(String callback);

    default Long getIdFromString(CallbackFormat format, String callback) {
        Pattern pattern = Pattern.compile(format.getIdRegex());
        Matcher matcher = pattern.matcher(callback);
        if (!matcher.find()) {
            throw new IllegalStateException(String.format("Not found id for data string %s", callback));
        }
        String idString = matcher.group().replaceAll("[\\]\\[]", "");
        return Long.valueOf(idString);
    }
}
