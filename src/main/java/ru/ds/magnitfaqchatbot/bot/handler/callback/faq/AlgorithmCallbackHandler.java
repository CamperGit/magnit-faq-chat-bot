package ru.ds.magnitfaqchatbot.bot.handler.callback.faq;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ds.magnitfaqchatbot.bot.MagnitFaqChatBot;
import ru.ds.magnitfaqchatbot.bot.handler.callback.BotCallbackHandler;
import ru.ds.magnitfaqchatbot.bot.handler.callback.CallbackFormat;
import ru.ds.magnitfaqchatbot.service.LocaleMessageSource;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AlgorithmCallbackHandler implements BotCallbackHandler {

    static String UNKNOWN_COMMAND_MESSAGE_SOURCE = "main.unknown-non-command-message";

    LocaleMessageSource localeMessageSource;

    @Override
    public void handle(MagnitFaqChatBot bot, Update update) throws TelegramApiException {

    }

    @Override
    public boolean isApplicable(String callback) {
        return Pattern.compile(CallbackFormat.ALGORITHM_FORMAT.getApplicableRegex()).matcher(callback).find();
    }
}
