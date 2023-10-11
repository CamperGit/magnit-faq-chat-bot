package ru.ds.magnitfaqchatbot.bot.handler.callback;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum CallbackFormat {

    ALGORITHM_FORMAT("algorithm.[%d]", "algorithm.[\\d]", "[\\]\\[]");

    String value;
    String applicableRegex;
    String idRegex;
}
