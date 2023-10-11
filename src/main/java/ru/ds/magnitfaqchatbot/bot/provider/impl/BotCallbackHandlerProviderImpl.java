package ru.ds.magnitfaqchatbot.bot.provider.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.ds.magnitfaqchatbot.bot.handler.callback.BotCallbackHandler;
import ru.ds.magnitfaqchatbot.bot.provider.BotCallbackHandlerProvider;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BotCallbackHandlerProviderImpl implements BotCallbackHandlerProvider {

    List<BotCallbackHandler> callbackHandlers;

    @Override
    public BotCallbackHandler getBotCallbackHandler(String callback) {
        return callbackHandlers.stream()
                .filter((handler) -> handler.isApplicable(callback))
                .findFirst()
                .orElse(null);
    }
}
