package ru.ds.magnitfaqchatbot.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.ds.magnitfaqchatbot.entity.AlgorithmEntity;
import ru.ds.magnitfaqchatbot.entity.FaqEntity;
import ru.ds.magnitfaqchatbot.entity.specification.Specifications;
import ru.ds.magnitfaqchatbot.model.faq.FaqSearchPayload;
import ru.ds.magnitfaqchatbot.repository.AlgorithmRepository;
import ru.ds.magnitfaqchatbot.repository.FaqRepository;
import ru.ds.magnitfaqchatbot.service.AlgorithmService;
import ru.ds.magnitfaqchatbot.service.ExampleService;
import ru.ds.magnitfaqchatbot.service.FaqService;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ru.ds.magnitfaqchatbot.entity.specification.Specifications.inOrReturnNull;
import static ru.ds.magnitfaqchatbot.entity.specification.Specifications.likeOrReturnNull;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FaqServiceImpl implements FaqService {

    static String ID = "id";

    FaqRepository faqRepository;

    AlgorithmService algorithmService;

    @Override
    public FaqEntity save(FaqEntity faq) {
        List<AlgorithmEntity> algorithms = faq.getAlgorithms();
        if (!CollectionUtils.isEmpty(algorithms)) {
            faq.setAlgorithms(algorithmService.saveAll(algorithms));
        }
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

    @Override
    public Page<FaqEntity> search(FaqSearchPayload searchPayload) {
        Sort.Direction sortDirection = searchPayload.getSortDirection();
        String sortProperty = searchPayload.getSortProperty();
        int pageNumber = searchPayload.getPageNumber();
        int pageSize = searchPayload.getPageSize();

        if (CollectionUtils.isEmpty(searchPayload.getCategoriesIn())) {
            return Page.empty();
        }

        List<Specification<FaqEntity>> specifications = Arrays.asList(
                Specifications.Or.<FaqEntity>builder()
                                .specifications(Arrays.asList(
                                        likeOrReturnNull("title", searchPayload.getTitleLike(), FaqEntity.class),
                                        likeOrReturnNull("question", searchPayload.getQuestionLike(), FaqEntity.class)
                                )).build(),
                inOrReturnNull("category.title", searchPayload.getCategoriesIn(), FaqEntity.class)
        );

        return faqRepository.findAll(
                Specifications.And.<FaqEntity>builder()
                        .specifications(specifications)
                        .build(),
                sortDirection == null || StringUtils.isEmpty(sortProperty)
                        ? PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, ID)
                        : PageRequest.of(pageNumber, pageSize, sortDirection, sortProperty));
    }
}
