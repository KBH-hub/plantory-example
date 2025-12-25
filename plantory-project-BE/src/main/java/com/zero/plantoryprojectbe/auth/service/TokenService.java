package com.zero.plantoryprojectbe.auth.service;

import com.zero.plantoryprojectbe.global.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;

    public String createNewAccessToken(String refreshToken) {

        if (tokenProvider.validateToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Long userId = refreshTokenService
                .findByRefreshToken(refreshToken)
                .getMemberId();

        return tokenProvider.createAccessToken(String.valueOf(userId));
    }
}


