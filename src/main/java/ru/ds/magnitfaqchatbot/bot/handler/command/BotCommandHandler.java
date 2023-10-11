package ru.ds.magnitfaqchatbot.bot.handler.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ds.magnitfaqchatbot.bot.MagnitFaqChatBot;

public interface BotCommandHandler {

    void handle(MagnitFaqChatBot bot, Update update) throws TelegramApiException;

    boolean isApplicable(String message);
}
