package ru.ds.magnitfaqchatbot.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.ds.magnitfaqchatbot.entity.AlgorithmEntity;
import ru.ds.magnitfaqchatbot.entity.ExampleEntity;
import ru.ds.magnitfaqchatbot.repository.AlgorithmRepository;
import ru.ds.magnitfaqchatbot.service.AlgorithmService;
import ru.ds.magnitfaqchatbot.service.ExampleService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AlgorithmServiceImpl implements AlgorithmService {

    AlgorithmRepository algorithmRepository;

    ExampleService exampleService;

    @Override
    public AlgorithmEntity save(AlgorithmEntity algorithm) {
        List<ExampleEntity> examples = algorithm.getExamples();
        if (!CollectionUtils.isEmpty(examples)) {
            algorithm.setExamples(exampleService.saveAll(examples));
        }
        return algorithmRepository.save(algorithm);
    }

    @Override
    public List<AlgorithmEntity> saveAll(List<AlgorithmEntity> algorithms) {
        List<AlgorithmEntity> savedAlgorithms = new ArrayList<>();
        for (AlgorithmEntity algorithm : algorithms) {
            savedAlgorithms.add(save(algorithm));
        }
        return savedAlgorithms;
    }

    @Override
    public AlgorithmEntity getById(Long id) {
        return algorithmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Not found algorithm for id = %d", id)));
    }

    @Override
    public void deleteById(Long id) {
        algorithmRepository.deleteById(id);
    }
}
