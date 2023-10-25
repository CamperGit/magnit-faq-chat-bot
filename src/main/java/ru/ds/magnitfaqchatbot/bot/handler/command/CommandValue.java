package ru.ds.magnitfaqchatbot.bot.handler.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Список констант команд, обрабатываемых телеграмм сообщений
 */
@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum CommandValue {

    SET_SEARCH_LIMIT("/set_search_limit"),
    SYNCHRONIZE_USER("/synchronize"),
    GET_USER_ID("/get_user_id");

    String value;
}
