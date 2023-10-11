package ru.ds.magnitfaqchatbot.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.ds.magnitfaqchatbot.entity.AlgorithmEntity;
import ru.ds.magnitfaqchatbot.repository.AlgorithmRepository;
import ru.ds.magnitfaqchatbot.service.AlgorithmService;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AlgorithmServiceImpl implements AlgorithmService {

    AlgorithmRepository algorithmRepository;

    @Override
    public AlgorithmEntity save(AlgorithmEntity algorithm) {
        return algorithmRepository.save(algorithm);
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
