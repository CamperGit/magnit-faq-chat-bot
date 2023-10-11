package ru.ds.magnitfaqchatbot.bot.handler.command;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
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
import ru.ds.magnitfaqchatbot.model.role.CategoryPermission;
import ru.ds.magnitfaqchatbot.model.role.RolePermission;
import ru.ds.magnitfaqchatbot.service.FaqService;
import ru.ds.magnitfaqchatbot.service.LocaleMessageSource;
import ru.ds.magnitfaqchatbot.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SearchFaqCommandHandler implements BotCommandHandler {

    static int PAGE_NUMBER = 0;

    static int PAGE_SIZE = 5;

    static String SEARCH_NOT_FOUND_MESSAGE_SOURCE = "search.not-found";

    LocaleMessageSource localeMessageSource;

    MarkupGenerator markupGenerator;

    FaqService faqService;

    UserService userService;

    Mapper mapper;

    @Override
    public void handle(MagnitFaqChatBot bot, Update update) throws TelegramApiException {
        String chatId = update.getMessage().getChatId().toString();
        UserDto user = mapper.map(userService.getByTelegramId(chatId), UserDto.class);
        String text = update.getMessage().getText();
        Page<FaqEntity> search = faqService.search(FaqSearchPayload.builder()
                .questionLike(text)
                .titleLike(text)
                .categoriesIn(getUserCategoriesIn(user))
                .pageNumber(PAGE_NUMBER)
                .pageSize(PAGE_SIZE)
                .build());

        if (search.isEmpty()) {
            bot.execute(SendMessage.builder()
                    .chatId(chatId)
                    .text(localeMessageSource.getMessage(SEARCH_NOT_FOUND_MESSAGE_SOURCE))
                    .build());
        }

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
        return user.getRoles()
                .stream()
                .map(UserRoleDto::getPermissions)
                .map(RolePermission::getCategoryPermissions)
                .map(CategoryPermission::getCategories)
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    private void generateAndSendFaqMessage(MagnitFaqChatBot bot, String chatId, FaqDto faqDto) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage(chatId, faqDto.getTitle());
        sendMessage.setReplyMarkup(markupGenerator.getFaqMarkup(faqDto));
        bot.execute(sendMessage);
    }
}
