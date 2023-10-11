package ru.ds.magnitfaqchatbot.service;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import ru.ds.magnitfaqchatbot.entity.ExampleEntity;
import ru.ds.magnitfaqchatbot.entity.FaqEntity;
import ru.ds.magnitfaqchatbot.model.faq.FaqSearchPayload;


public interface FaqService {

    FaqEntity save(FaqEntity faq);

    @Transactional
    FaqEntity getById(Long id);

    void deleteById(Long id);

    @Transactional
    Page<FaqEntity> search(FaqSearchPayload searchPayload);
}
