package ru.ds.magnitfaqchatbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.ds.magnitfaqchatbot.entity.FaqEntity;

@Repository
public interface FaqRepository extends JpaRepository<FaqEntity, Long>,
        JpaSpecificationExecutor<FaqEntity> {
}
