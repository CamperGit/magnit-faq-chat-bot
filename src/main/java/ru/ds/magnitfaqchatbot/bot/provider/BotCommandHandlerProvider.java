package ru.ds.magnitfaqchatbot.bot.provider;


import ru.ds.magnitfaqchatbot.bot.handler.command.BotCommandHandler;

public interface BotCommandHandlerProvider {

    BotCommandHandler getBotCommandHandler(String message);
}
