package com.diary.auth.security;

import com.diary.auth.model.User;
import com.diary.auth.repository.BlacklistTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtAccessExpirationInMs}")
    private long jwtAccessExpirationInMs;

    @Value("${app.jwtRefreshExpirationInMs}")
    private long jwtRefreshExpirationInMs;

    private final BlacklistTokenRepository blacklistTokenRepository;

    private Key jwtKey;

    public JwtTokenProvider(BlacklistTokenRepository blacklistTokenRepository) {
        this.blacklistTokenRepository = blacklistTokenRepository;
    }

    @PostConstruct
    public void init() {
        this.jwtKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateRefreshToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtRefreshExpirationInMs);

        Claims claims = Jwts.claims();
        claims.setSubject(user.getId());
        claims.setIssuedAt(now);
        claims.setExpiration(expiryDate);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(jwtKey)
                .compact();
    }

    public String generateAccessToken(String refreshToken) {
        Claims refreshClaims = getClaims(refreshToken);
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtAccessExpirationInMs);

        Claims accessClaims = Jwts.claims();
        accessClaims.put("userId", refreshClaims.getSubject());
        accessClaims.setSubject(refreshClaims.getSubject());
        accessClaims.setIssuedAt(now);
        accessClaims.setExpiration(expiryDate);

        return Jwts.builder()
                .setClaims(accessClaims)
                .signWith(jwtKey)
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validateRefreshToken(String token, User user) {
        Claims claims = getClaims(token);
        if (claims.getExpiration().before(new Date())) {
            return false;
        }
        if (!claims.getSubject().equals(user.getId())) {
            return false;
        }
        if (blacklistTokenRepository.existsByToken(token)) {
            return false;
        }
        return true;
    }

}
