package ru.ds.magnitfaqchatbot.bot.handler.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum CommandValue {

    SET_SEARCH_LIMIT("/set_search_limit");

    String value;
}
