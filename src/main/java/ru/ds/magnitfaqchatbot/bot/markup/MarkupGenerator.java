package ru.ds.magnitfaqchatbot.bot.markup;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.ds.magnitfaqchatbot.bot.handler.callback.CallbackFormat;
import ru.ds.magnitfaqchatbot.dto.AlgorithmDto;
import ru.ds.magnitfaqchatbot.dto.FaqDto;
import ru.ds.magnitfaqchatbot.entity.FaqEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MarkupGenerator {

    public InlineKeyboardMarkup getFaqMarkup(FaqDto faq) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (AlgorithmDto algorithm : faq.getAlgorithms()) {
            InlineKeyboardButton algorithmButton = new InlineKeyboardButton(algorithm.getTitle());
            algorithmButton.setCallbackData(String.format(CallbackFormat.ALGORITHM_FORMAT.getValue(), algorithm.getId()));
            rowList.add(Collections.singletonList(algorithmButton));
        }
        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
}
