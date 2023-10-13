package ru.ds.magnitfaqchatbot.bot.handler.callback;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Формат данных для обработчиков колбеков телеграм сообщений
 */
@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum CallbackFormat {

    ALGORITHM_FORMAT("algorithm.[%d]", "algorithm.\\[\\d{0,}\\]", "\\[\\d{0,}\\]"),
    EXAMPLE_FORMAT("example.[%d]", "example.\\[\\d{0,}\\]", "\\[\\d{0,}\\]");

    String value;
    String applicableRegex;
    String idRegex;
}
