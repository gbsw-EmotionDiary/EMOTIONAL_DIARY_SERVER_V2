package com.diary.auth.repository;

import com.diary.auth.model.Token;
import com.diary.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, String> {
    Token findByRefreshToken(String refreshToken);

    Optional<Token> findByUser(User user);
}
