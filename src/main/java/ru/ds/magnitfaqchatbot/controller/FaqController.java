package ru.ds.magnitfaqchatbot.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ds.magnitfaqchatbot.dto.FaqDto;
import ru.ds.magnitfaqchatbot.entity.FaqEntity;
import ru.ds.magnitfaqchatbot.mapper.Mapper;
import ru.ds.magnitfaqchatbot.service.FaqService;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/v1/faq")
public class FaqController {

    FaqService faqService;

    Mapper mapper;

    @PostMapping
    public ResponseEntity<FaqDto> create(@RequestBody FaqDto faq) {
        return ResponseEntity.ok(mapper.map(faqService.save(mapper.map(faq, FaqEntity.class)), FaqDto.class));
    }
}
