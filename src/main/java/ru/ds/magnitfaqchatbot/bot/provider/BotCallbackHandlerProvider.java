package ru.ds.magnitfaqchatbot.bot.provider;


import ru.ds.magnitfaqchatbot.bot.handler.callback.BotCallbackHandler;

public interface BotCallbackHandlerProvider {

    BotCallbackHandler getBotCallbackHandler(String callback);
}
