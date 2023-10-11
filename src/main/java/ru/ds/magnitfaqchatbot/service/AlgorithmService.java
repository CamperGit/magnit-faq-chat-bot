package ru.ds.magnitfaqchatbot.service;

import ru.ds.magnitfaqchatbot.entity.AlgorithmEntity;

import java.util.List;


public interface AlgorithmService {

    AlgorithmEntity save(AlgorithmEntity algorithm);

    List<AlgorithmEntity> saveAll(List<AlgorithmEntity> algorithms);

    AlgorithmEntity getById(Long id);

    void deleteById(Long id);
}
