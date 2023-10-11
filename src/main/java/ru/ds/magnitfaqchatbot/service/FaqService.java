package ru.ds.magnitfaqchatbot.service;

import ru.ds.magnitfaqchatbot.entity.ExampleEntity;
import ru.ds.magnitfaqchatbot.entity.FaqEntity;


public interface FaqService {

    FaqEntity save(FaqEntity faq);

    FaqEntity getById(Long id);

    void deleteById(Long id);
}
