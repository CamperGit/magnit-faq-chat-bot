package ru.ds.magnitfaqchatbot.exception;

public class MagnitFaqChatBotTelegramException extends RuntimeException {

    public MagnitFaqChatBotTelegramException() {
    }

    public MagnitFaqChatBotTelegramException(String message) {
        super(message);
    }

    public MagnitFaqChatBotTelegramException(String message, Throwable cause) {
        super(message, cause);
    }
}
