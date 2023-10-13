package ru.ds.magnitfaqchatbot.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.ds.magnitfaqchatbot.bot.MagnitFaqChatBot;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BotConfiguration {

    MagnitFaqChatBot telegramBot;

    @PostConstruct
    void postConstruct() throws TelegramApiException {
        // Регистрация бота
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(telegramBot);
    }
}
