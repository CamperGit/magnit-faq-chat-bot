package ru.ds.magnitfaqchatbot.service;

import ru.ds.magnitfaqchatbot.entity.AlgorithmEntity;


public interface AlgorithmService {

    AlgorithmEntity save(AlgorithmEntity algorithm);

    AlgorithmEntity getById(Long id);

    void deleteById(Long id);
}
