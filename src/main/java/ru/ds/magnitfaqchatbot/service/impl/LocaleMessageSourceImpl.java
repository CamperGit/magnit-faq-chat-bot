package ru.ds.magnitfaqchatbot.service.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.ds.magnitfaqchatbot.property.LocaleProperties;
import ru.ds.magnitfaqchatbot.service.LocaleMessageSource;

import java.util.Locale;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LocaleMessageSourceImpl implements LocaleMessageSource {

    Locale locale;

    MessageSource messageSource;

    public LocaleMessageSourceImpl(LocaleProperties localeProperties, MessageSource messageSource) {
        this.locale = Locale.forLanguageTag(localeProperties.getTag());
        this.messageSource = messageSource;
    }

    public String getMessage(String key) {
        return messageSource.getMessage(key, null, locale);
    }
}
