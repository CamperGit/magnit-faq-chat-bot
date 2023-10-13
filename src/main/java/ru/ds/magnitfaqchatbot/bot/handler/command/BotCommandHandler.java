package ru.ds.magnitfaqchatbot.bot.handler.command;

import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ds.magnitfaqchatbot.bot.MagnitFaqChatBot;
import ru.ds.magnitfaqchatbot.service.BotUtilService;

public interface BotCommandHandler extends BotUtilService {

    /**
     * Метод обработки команды
     *
     * @param bot    - бот
     * @param update - модель данных телеграм сообщения
     * @throws TelegramApiException
     */
    @Transactional
    void handle(MagnitFaqChatBot bot, Update update) throws TelegramApiException;

    /**
     * Проверка на применимость обработчика
     *
     * @param message - команда
     * @return применим ли обработчик
     */
    boolean isApplicable(String message);
}
