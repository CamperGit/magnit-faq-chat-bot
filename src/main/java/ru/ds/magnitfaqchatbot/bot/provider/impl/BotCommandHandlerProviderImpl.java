package ru.ds.magnitfaqchatbot.bot.provider.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.ds.magnitfaqchatbot.bot.handler.command.BotCommandHandler;
import ru.ds.magnitfaqchatbot.bot.handler.command.SearchFaqCommandHandler;
import ru.ds.magnitfaqchatbot.bot.provider.BotCommandHandlerProvider;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BotCommandHandlerProviderImpl implements BotCommandHandlerProvider {

    SearchFaqCommandHandler searchFaqCommandHandler;

    List<BotCommandHandler> commandHandlers;

    @Override
    public BotCommandHandler getBotCommandHandler(String message) {
        if (StringUtils.isEmpty(message)) {
            return searchFaqCommandHandler;
        }
        return commandHandlers.stream()
                .filter((handler) -> handler.isApplicable(message))
                .findFirst()
                .orElse(searchFaqCommandHandler);
    }
}
