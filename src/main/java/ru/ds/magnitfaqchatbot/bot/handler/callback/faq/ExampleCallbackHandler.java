package ru.ds.magnitfaqchatbot.bot.handler.callback.faq;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ds.magnitfaqchatbot.bot.MagnitFaqChatBot;
import ru.ds.magnitfaqchatbot.bot.handler.callback.BotCallbackHandler;
import ru.ds.magnitfaqchatbot.bot.handler.callback.CallbackFormat;
import ru.ds.magnitfaqchatbot.bot.markup.MarkupGenerator;
import ru.ds.magnitfaqchatbot.dto.AlgorithmDto;
import ru.ds.magnitfaqchatbot.dto.ExampleDto;
import ru.ds.magnitfaqchatbot.entity.AlgorithmEntity;
import ru.ds.magnitfaqchatbot.entity.ExampleEntity;
import ru.ds.magnitfaqchatbot.mapper.Mapper;
import ru.ds.magnitfaqchatbot.service.AlgorithmService;
import ru.ds.magnitfaqchatbot.service.ExampleService;
import ru.ds.magnitfaqchatbot.service.LocaleMessageSource;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExampleCallbackHandler implements BotCallbackHandler {

    static String PRECONDITION_FORMAT = "%s: ";
    static String SOLUTION_FORMAT = "%s: ";
    static String EXAMPLE_PRECONDITION_MESSAGE_SOURCE = "example.precondition";
    static String EXAMPLE_SOLUTION_MESSAGE_SOURCE = "example.solution";

    LocaleMessageSource localeMessageSource;
    ExampleService exampleService;
    Mapper mapper;

    @Override
    public void handle(MagnitFaqChatBot bot, Update update) throws TelegramApiException {
        // Получение примера из БД
        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        Long id = getIdFromString(CallbackFormat.EXAMPLE_FORMAT, update.getCallbackQuery().getData());
        ExampleEntity example = exampleService.getById(id);
        ExampleDto exampleDto = mapper.map(example, ExampleDto.class);

        // Отправка сообщений
        SendMessage sendMessage = new SendMessage(chatId, getExampleText(exampleDto));
        sendMessage.enableHtml(true);
        bot.execute(sendMessage);
    }

    @Override
    public boolean isApplicable(String callback) {
        return Pattern.compile(CallbackFormat.EXAMPLE_FORMAT.getApplicableRegex()).matcher(callback).find();
    }

    // Получение текста примера
    private String getExampleText(ExampleDto example) {
        return new StringBuilder(wrapInBold(String.format(PRECONDITION_FORMAT, localeMessageSource.getMessage(EXAMPLE_PRECONDITION_MESSAGE_SOURCE))))
                .append(addWrapLine(example.getPrecondition()))
                .append(addWrapLine())
                .append(wrapInBold(String.format(SOLUTION_FORMAT, localeMessageSource.getMessage(EXAMPLE_SOLUTION_MESSAGE_SOURCE))))
                .append(addWrapLine(example.getSolution()))
                .toString();
    }
}
