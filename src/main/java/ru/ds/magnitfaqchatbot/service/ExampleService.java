package ru.ds.magnitfaqchatbot.service;

import ru.ds.magnitfaqchatbot.entity.ExampleEntity;


public interface ExampleService {

    ExampleEntity save(ExampleEntity example);

    ExampleEntity getById(Long id);

    void deleteById(Long id);
}
