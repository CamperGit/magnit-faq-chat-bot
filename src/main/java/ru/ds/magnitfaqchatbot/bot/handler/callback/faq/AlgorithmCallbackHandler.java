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
import ru.ds.magnitfaqchatbot.dto.FaqDto;
import ru.ds.magnitfaqchatbot.entity.AlgorithmEntity;
import ru.ds.magnitfaqchatbot.mapper.Mapper;
import ru.ds.magnitfaqchatbot.service.AlgorithmService;
import ru.ds.magnitfaqchatbot.service.LocaleMessageSource;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AlgorithmCallbackHandler implements BotCallbackHandler {

    static String TEXT_FORMAT = "%s: ";
    static String ALGORITHM_TEXT_MESSAGE_SOURCE = "algorithm.text";
    static String ALGORITHM_EXAMPLES_MESSAGE_SOURCE = "algorithm.examples";

    LocaleMessageSource localeMessageSource;
    MarkupGenerator markupGenerator;
    AlgorithmService algorithmService;
    Mapper mapper;

    @Override
    public void handle(MagnitFaqChatBot bot, Update update) throws TelegramApiException {
        // Получение алгоритма из БД
        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        Long id = getIdFromString(CallbackFormat.ALGORITHM_FORMAT, update.getCallbackQuery().getData());
        AlgorithmEntity algorithm = algorithmService.getById(id);
        AlgorithmDto algorithmDto = mapper.map(algorithm, AlgorithmDto.class);

        // Отправка сообщений
        SendMessage sendMessage = new SendMessage(chatId, getAlgorithmText(algorithmDto));
        sendMessage.setReplyMarkup(markupGenerator.getAlgorithmMarkup(algorithmDto));
        sendMessage.enableHtml(true);
        bot.execute(sendMessage);
    }

    @Override
    public boolean isApplicable(String callback) {
        return Pattern.compile(CallbackFormat.ALGORITHM_FORMAT.getApplicableRegex()).matcher(callback).find();
    }

    // Получение текста алгоритма
    private String getAlgorithmText(AlgorithmDto algorithm) {
        StringBuilder builder = new StringBuilder(addWrapLine(wrapInBold(algorithm.getTitle())))
                .append(addWrapLine(wrapInBold(String.format(TEXT_FORMAT, localeMessageSource.getMessage(ALGORITHM_TEXT_MESSAGE_SOURCE)))))
                .append(algorithm.getText());

        if (!CollectionUtils.isEmpty(algorithm.getExamples())) {
            builder.append(addWrapLine())
                    .append(addWrapLine())
                    .append(wrapInBold(localeMessageSource.getMessage(ALGORITHM_EXAMPLES_MESSAGE_SOURCE)));
        }

        return builder.toString();
    }
}
