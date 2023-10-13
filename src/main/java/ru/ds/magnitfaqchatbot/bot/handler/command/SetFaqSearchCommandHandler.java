package ru.ds.magnitfaqchatbot.bot.handler.command;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ds.magnitfaqchatbot.bot.MagnitFaqChatBot;
import ru.ds.magnitfaqchatbot.dto.UserDto;
import ru.ds.magnitfaqchatbot.entity.UserEntity;
import ru.ds.magnitfaqchatbot.mapper.Mapper;
import ru.ds.magnitfaqchatbot.model.user.SearchSettings;
import ru.ds.magnitfaqchatbot.model.user.UserSettings;
import ru.ds.magnitfaqchatbot.service.LocaleMessageSource;
import ru.ds.magnitfaqchatbot.service.UserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Обработчик команды на установку лимита поиска ЧаВо
 */
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SetFaqSearchCommandHandler implements BotCommandHandler {

    static String SEARCH_LIMIT_CHANGED_MESSAGE_SOURCE = "search.limit-changed";
    static String LIMIT_GROUP = "limitGroup";
    static String COMMAND_PATTERN = String.format("%s (?<%s>(\\d{1,3}))$", CommandValue.SET_SEARCH_LIMIT.getValue(), LIMIT_GROUP);

    UserService userService;
    LocaleMessageSource localeMessageSource;
    Mapper mapper;

    @Override
    public void handle(MagnitFaqChatBot bot, Update update) throws TelegramApiException {
        Message message = update.getMessage();
        String chatId = message.getChatId().toString();
        String text = message.getText();
        //Поиск лимита поиска ЧаВо
        Matcher limitMatcher = Pattern.compile(COMMAND_PATTERN).matcher(text);
        if (limitMatcher.find()) {
            String limitGroup = limitMatcher.group(LIMIT_GROUP);
            //Лимит
            int newFaqSearchLimit = Integer.parseInt(limitGroup);
            //Получение и обновление лимита поиска ЧаВо и сохранения настроек пользователя
            UserDto user = mapper.map(userService.getByTelegramId(chatId), UserDto.class);
            user.setSettings(UserSettings.builder()
                            .searchSettings(SearchSettings.builder()
                                    .faqPageSize(newFaqSearchLimit)
                                    .build())
                    .build());
            userService.save(mapper.map(user, UserEntity.class));
            SendMessage sendMessage = new SendMessage(chatId, localeMessageSource.getMessage(SEARCH_LIMIT_CHANGED_MESSAGE_SOURCE));
            bot.execute(sendMessage);
        }
    }

    @Override
    public boolean isApplicable(String message) {
        return message.contains(CommandValue.SET_SEARCH_LIMIT.getValue());
    }
}
