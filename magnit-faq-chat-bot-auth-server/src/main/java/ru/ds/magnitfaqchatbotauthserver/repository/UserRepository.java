package ru.ds.magnitfaqchatbotauthserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ds.magnitfaqchatbotauthserver.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByTelegramId(Long telegramId);
}
