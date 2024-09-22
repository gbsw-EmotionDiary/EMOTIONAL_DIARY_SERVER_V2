package com.diary.auth.repository;

import com.diary.auth.model.BlacklistToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistTokenRepository extends JpaRepository<BlacklistToken, String> {
    boolean existsByToken(String token);
}
