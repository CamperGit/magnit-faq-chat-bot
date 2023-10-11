package ru.ds.magnitfaqchatbot.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.ds.magnitfaqchatbot.entity.FaqEntity;
import ru.ds.magnitfaqchatbot.repository.FaqRepository;
import ru.ds.magnitfaqchatbot.service.FaqService;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FaqServiceImpl implements FaqService {

    FaqRepository faqRepository;

    @Override
    public FaqEntity save(FaqEntity faq) {
        return faqRepository.save(faq);
    }

    @Override
    public FaqEntity getById(Long id) {
        return faqRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Not found faq for id = %d", id)));
    }

    @Override
    public void deleteById(Long id) {
        faqRepository.deleteById(id);
    }
}
