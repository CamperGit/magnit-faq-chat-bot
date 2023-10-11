package ru.ds.magnitfaqchatbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ds.magnitfaqchatbot.entity.AlgorithmEntity;

@Repository
public interface AlgorithmRepository extends JpaRepository<AlgorithmEntity, Long> {
}
