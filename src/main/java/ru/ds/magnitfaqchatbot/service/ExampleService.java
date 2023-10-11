package ru.ds.magnitfaqchatbot.service;

import ru.ds.magnitfaqchatbot.entity.ExampleEntity;

import java.util.List;


public interface ExampleService {

    ExampleEntity save(ExampleEntity example);

    List<ExampleEntity> saveAll(List<ExampleEntity> examples);

    ExampleEntity getById(Long id);

    void deleteById(Long id);
}
