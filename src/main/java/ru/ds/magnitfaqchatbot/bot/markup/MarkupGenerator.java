package ru.ds.magnitfaqchatbot.bot.markup;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.criterion.Example;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.ds.magnitfaqchatbot.bot.handler.callback.CallbackFormat;
import ru.ds.magnitfaqchatbot.dto.AlgorithmDto;
import ru.ds.magnitfaqchatbot.dto.ExampleDto;
import ru.ds.magnitfaqchatbot.dto.FaqDto;
import ru.ds.magnitfaqchatbot.entity.FaqEntity;
import ru.ds.magnitfaqchatbot.property.ButtonLimitProperties;
import ru.ds.magnitfaqchatbot.service.LocaleMessageSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MarkupGenerator {

    LocaleMessageSource localeMessageSource;
    ButtonLimitProperties buttonLimitProperties;

    static String EXAMPLE_BUTTON_TITLE_FORMAT = "%s%d";
    static String EXAMPLE_TITLE_MESSAGE_SOURCE = "example.title";

    public InlineKeyboardMarkup getFaqMarkup(FaqDto faq) {
        if (CollectionUtils.isEmpty(faq.getAlgorithms())) {
            return null;
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<AlgorithmDto> algorithms = faq.getAlgorithms();
        List<AlgorithmDto> algorithmsSubList = algorithms.subList(0, Math.min(algorithms.size(), buttonLimitProperties.getAlgorithms()));
        for (AlgorithmDto algorithm : algorithmsSubList) {
            InlineKeyboardButton algorithmButton = new InlineKeyboardButton(algorithm.getTitle());
            algorithmButton.setCallbackData(String.format(CallbackFormat.ALGORITHM_FORMAT.getValue(), algorithm.getId()));
            rowList.add(Collections.singletonList(algorithmButton));
        }
        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getAlgorithmMarkup(AlgorithmDto algorithmDto) {
        if (CollectionUtils.isEmpty(algorithmDto.getExamples())) {
            return null;
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<ExampleDto> examples = algorithmDto.getExamples();
        List<ExampleDto> examplesSublist = examples.subList(0, Math.min(examples.size(), buttonLimitProperties.getExamples()));
        for (int exampleNum = 1; exampleNum <= examplesSublist.size(); exampleNum++) {
            InlineKeyboardButton exampleButton = new InlineKeyboardButton(
                    String.format(EXAMPLE_BUTTON_TITLE_FORMAT, localeMessageSource.getMessage(EXAMPLE_TITLE_MESSAGE_SOURCE), exampleNum)
            );
            ExampleDto example = examplesSublist.get(exampleNum - 1);
            exampleButton.setCallbackData(String.format(CallbackFormat.EXAMPLE_FORMAT.getValue(), example.getId()));
            rowList.add(Collections.singletonList(exampleButton));
        }
        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
}
