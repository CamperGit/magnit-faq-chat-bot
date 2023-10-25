package ru.ds.magnitfaqchatbot.bot.handler.command;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ds.magnitfaqchatbot.bot.MagnitFaqChatBot;
import ru.ds.magnitfaqchatbot.bot.markup.MarkupGenerator;
import ru.ds.magnitfaqchatbot.dto.FaqDto;
import ru.ds.magnitfaqchatbot.dto.UserDto;
import ru.ds.magnitfaqchatbot.dto.UserRoleDto;
import ru.ds.magnitfaqchatbot.entity.FaqEntity;
import ru.ds.magnitfaqchatbot.mapper.Mapper;
import ru.ds.magnitfaqchatbot.model.faq.FaqSearchPayload;
import ru.ds.magnitfaqchatbot.service.FaqService;
import ru.ds.magnitfaqchatbot.service.LocaleMessageSource;
import ru.ds.magnitfaqchatbot.service.RoleCategoryResolverService;
import ru.ds.magnitfaqchatbot.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Обработчик поиска ЧаВо
 */
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SearchFaqCommandHandler implements BotCommandHandler {

    static int PAGE_NUMBER = 0;

    static String SEARCH_NOT_FOUND_MESSAGE_SOURCE = "search.not-found";
    static String FAQ_QUESTION_MESSAGE_SOURCE = "faq.question";
    static String FAQ_CATEGORY_MESSAGE_SOURCE = "faq.category";
    static String FAQ_ANSWER_MESSAGE_SOURCE = "faq.answer";
    static String FAQ_ALGORITHMS_MESSAGE_SOURCE = "faq.algorithms";

    LocaleMessageSource localeMessageSource;

    MarkupGenerator markupGenerator;

    FaqService faqService;

    UserService userService;

    RoleCategoryResolverService roleCategoryResolverService;

    Mapper mapper;

    @Override
    public void handle(MagnitFaqChatBot bot, Update update) throws TelegramApiException {
        //Получение начальных данных
        Long telegramUserId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        UserDto user = mapper.map(userService.getByTelegramId(telegramUserId), UserDto.class);
        String text = update.getMessage().getText();

        //Поиск
        Page<FaqEntity> search = faqService.search(FaqSearchPayload.builder()
                .questionLike(text)
                .titleLike(text)
                .categoriesIn(getUserCategoriesIn(user))
                .pageNumber(PAGE_NUMBER)
                .pageSize(user.getSettings().getSearchSettings().getFaqPageSize())
                .build());

        //Отправка сообщения, если ничего не найдено
        if (search.isEmpty()) {
            bot.execute(SendMessage.builder()
                    .chatId(chatId)
                    .text(localeMessageSource.getMessage(SEARCH_NOT_FOUND_MESSAGE_SOURCE))
                    .build());
        }

        //Отправка сообщений по каждому из ЧаВо
        List<FaqDto> faqDtos = mapper.mapAsList(search.getContent(), FaqDto.class);
        for (FaqDto faq : faqDtos) {
            generateAndSendFaqMessage(bot, chatId, faq);
        }
    }

    @Override
    public boolean isApplicable(String message) {
        return false;
    }

    private List<String> getUserCategoriesIn(UserDto user) {
        List<String> roles = user.getRoles().stream().map(UserRoleDto::getTitle).collect(Collectors.toList());
        return roleCategoryResolverService.resolveCategories(roles);
    }

    private void generateAndSendFaqMessage(MagnitFaqChatBot bot, String chatId, FaqDto faqDto) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage(chatId, getFaqText(faqDto));
        sendMessage.setReplyMarkup(markupGenerator.getFaqMarkup(faqDto));
        sendMessage.enableHtml(true);
        bot.execute(sendMessage);
    }

    private String getFaqText(FaqDto faq) {
        StringBuilder builder = new StringBuilder(addWrapLine(wrapInBold(faq.getTitle())))
                .append(addWrapLine())
                .append(wrapInBold(String.format("%s: ", localeMessageSource.getMessage(FAQ_CATEGORY_MESSAGE_SOURCE))))
                .append(addWrapLine(faq.getCategory().getTitle()))
                .append(wrapInBold(String.format("%s: ", localeMessageSource.getMessage(FAQ_QUESTION_MESSAGE_SOURCE))))
                .append(addWrapLine(faq.getQuestion()))
                .append(wrapInBold(String.format("%s: ", localeMessageSource.getMessage(FAQ_ANSWER_MESSAGE_SOURCE))))
                .append(faq.getAnswer());

        if (!CollectionUtils.isEmpty(faq.getAlgorithms())) {
            builder.append(addWrapLine())
                    .append(addWrapLine())
                    .append(wrapInBold(localeMessageSource.getMessage(FAQ_ALGORITHMS_MESSAGE_SOURCE)));
        }

        return builder.toString();
    }
}
