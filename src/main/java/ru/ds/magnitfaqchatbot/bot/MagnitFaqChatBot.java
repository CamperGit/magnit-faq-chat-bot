package ru.ds.magnitfaqchatbot.bot;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ds.magnitfaqchatbot.bot.handler.callback.BotCallbackHandler;
import ru.ds.magnitfaqchatbot.bot.handler.command.BotCommandHandler;
import ru.ds.magnitfaqchatbot.bot.provider.BotCallbackHandlerProvider;
import ru.ds.magnitfaqchatbot.bot.provider.BotCommandHandlerProvider;
import ru.ds.magnitfaqchatbot.entity.UserEntity;
import ru.ds.magnitfaqchatbot.exception.MagnitFaqChatBotTelegramException;
import ru.ds.magnitfaqchatbot.property.TelegramBotProperties;
import ru.ds.magnitfaqchatbot.service.LocaleMessageSource;
import ru.ds.magnitfaqchatbot.service.UserService;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MagnitFaqChatBot extends TelegramLongPollingBot {

    TelegramBotProperties botProperties;

    BotCallbackHandlerProvider botCallbackHandlerProvider;

    BotCommandHandlerProvider botCommandHandlerProvider;

    UserService userService;

    public MagnitFaqChatBot(TelegramBotProperties botProperties,
                            BotCallbackHandlerProvider botCallbackHandlerProvider,
                            BotCommandHandlerProvider botCommandHandlerProvider,
                            UserService userService) {
        super(new DefaultBotOptions());
        this.botProperties = botProperties;
        this.botCallbackHandlerProvider = botCallbackHandlerProvider;
        this.botCommandHandlerProvider = botCommandHandlerProvider;
        this.userService = userService;
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
                CallbackQuery callbackQuery = update.getCallbackQuery();
                User fromUser = callbackQuery.getMessage().getFrom();
                createNewUserByTelegramId(fromUser.getId(), fromUser.getUserName());
                String callback = callbackQuery.getData();
                BotCallbackHandler botCallbackHandler = botCallbackHandlerProvider.getBotCallbackHandler(callback);
                if (botCallbackHandler != null) {
                    botCallbackHandler.handle(this, update);
                }
            }

            //Commands and nonCommands messages handling
            Message message = update.getMessage();
            if (message != null && message.hasText()) {
                User fromUser = message.getFrom();
                createNewUserByTelegramId(fromUser.getId(), fromUser.getUserName());
                String text = message.getText();
                BotCommandHandler botCommandHandler = botCommandHandlerProvider.getBotCommandHandler(text);
                botCommandHandler.handle(this, update);
            }
        } catch (TelegramApiException e) {
            log.error("Commands and none commands handling exception", e);
            throw new MagnitFaqChatBotTelegramException("Error while trying to handle telegram command or callback query", e);
        }
    }

    private UserEntity createNewUserByTelegramId(Long telegramId, String username) {
        return userService.getByTelegramIdOrCreate(telegramId, username);
    }
}
