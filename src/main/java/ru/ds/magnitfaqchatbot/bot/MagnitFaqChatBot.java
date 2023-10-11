package ru.ds.magnitfaqchatbot.bot;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ds.magnitfaqchatbot.bot.handler.callback.BotCallbackHandler;
import ru.ds.magnitfaqchatbot.bot.handler.command.BotCommandHandler;
import ru.ds.magnitfaqchatbot.bot.provider.BotCallbackHandlerProvider;
import ru.ds.magnitfaqchatbot.bot.provider.BotCommandHandlerProvider;
import ru.ds.magnitfaqchatbot.exception.MagnitFaqChatBotTelegramException;
import ru.ds.magnitfaqchatbot.property.TelegramBotProperties;
import ru.ds.magnitfaqchatbot.service.LocaleMessageSource;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MagnitFaqChatBot extends TelegramLongPollingBot {

    TelegramBotProperties botProperties;

    BotCallbackHandlerProvider botCallbackHandlerProvider;

    BotCommandHandlerProvider botCommandHandlerProvider;

    LocaleMessageSource localeMessageSource;

    public MagnitFaqChatBot(TelegramBotProperties botProperties,
                            BotCallbackHandlerProvider botCallbackHandlerProvider,
                            BotCommandHandlerProvider botCommandHandlerProvider, LocaleMessageSource localeMessageSource) {
        super(new DefaultBotOptions());
        this.botProperties = botProperties;
        this.botCallbackHandlerProvider = botCallbackHandlerProvider;
        this.botCommandHandlerProvider = botCommandHandlerProvider;
        this.localeMessageSource = localeMessageSource;
    }

    @Override
    public String getBotUsername() {
        return botProperties.getUsername();
    }

    @Override
    public String getBotToken() {
        return botProperties.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            //CallbackQuery handling
            if (update.hasCallbackQuery() && !update.getCallbackQuery().getData().equals("null")) {
                String callback = update.getCallbackQuery().getData();
                BotCallbackHandler botCallbackHandler = botCallbackHandlerProvider.getBotCallbackHandler(callback);
                if (botCallbackHandler != null) {
                    botCallbackHandler.handle(this, update);
                }
            }

            //Commands and nonCommands messages handling
            if (update.getMessage() != null && update.getMessage().hasText()) {
                String text = update.getMessage().getText();
                BotCommandHandler botCommandHandler = botCommandHandlerProvider.getBotCommandHandler(text);
                botCommandHandler.handle(this, update);
            }
        } catch (TelegramApiException e) {
            log.error("Commands and none commands handling exception", e);
            throw new MagnitFaqChatBotTelegramException("Error while trying to handle telegram command or callback query", e);
        }
    }
}
