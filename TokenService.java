package com.forumhub.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.secret}") private String secret;
    @Value("${jwt.expiration}") private long expirationMs;

    public String generate(String username) {
        var now = new Date();
        var exp = new Date(now.getTime() + expirationMs);
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(now)
                .withExpiresAt(exp)
                .withIssuer("forumhub")
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateAndGetSubject(String token) {
        var verifier = JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("forumhub")
                .build();
        return verifier.verify(token).getSubject();
    }
}

