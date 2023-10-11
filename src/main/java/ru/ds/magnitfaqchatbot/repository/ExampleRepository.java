package ru.ds.magnitfaqchatbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ds.magnitfaqchatbot.entity.ExampleEntity;

@Repository
public interface ExampleRepository extends JpaRepository<ExampleEntity, Long> {
}
