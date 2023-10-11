package ru.ds.magnitfaqchatbot.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.ds.magnitfaqchatbot.entity.ExampleEntity;
import ru.ds.magnitfaqchatbot.repository.ExampleRepository;
import ru.ds.magnitfaqchatbot.service.ExampleService;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExampleServiceImpl implements ExampleService {

    ExampleRepository exampleRepository;

    @Override
    public ExampleEntity save(ExampleEntity example) {
        return exampleRepository.save(example);
    }

    @Override
    public ExampleEntity getById(Long id) {
        return exampleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Not found example for id = %d", id)));
    }

    @Override
    public void deleteById(Long id) {
        exampleRepository.deleteById(id);
    }
}
