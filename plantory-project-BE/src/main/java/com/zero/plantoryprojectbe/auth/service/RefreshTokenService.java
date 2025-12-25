package com.zero.plantoryprojectbe.auth.service;

import com.zero.plantoryprojectbe.auth.dto.RefreshTokenRequest;
import com.zero.plantoryprojectbe.auth.RefreshTokenMapper;
import com.zero.plantoryprojectbe.global.utils.TokenHashUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenMapper refreshTokenMapper;
    private final TokenHashUtil tokenHashUtil;

    public RefreshTokenRequest findByRefreshToken(String refreshToken) {
        String tokenHash = tokenHashUtil.hash(refreshToken);

        RefreshTokenRequest token =
                refreshTokenMapper.selectByTokenHash(tokenHash);

        if (token == null) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        return token;
    }

    @Transactional
    public void deleteByToken(String refreshToken) {
        String tokenHash = tokenHashUtil.hash(refreshToken);
        refreshTokenMapper.deleteByTokenHash(tokenHash);
    }

    @Transactional
    public void deleteByUserId(Long memberId) {
        refreshTokenMapper.deleteByMemberId(memberId);
    }

    @Transactional
    public void save(Long memberId, String refreshToken) {
        String tokenHash = tokenHashUtil.hash(refreshToken);

        RefreshTokenRequest existing =
                refreshTokenMapper.selectByMemberId(memberId);

        if (existing == null) {
            RefreshTokenRequest token = new RefreshTokenRequest();
            token.setMemberId(memberId);
            token.setTokenHash(tokenHash);

            refreshTokenMapper.insertRefreshToken(token);
        } else {
            existing.setTokenHash(tokenHash);
            refreshTokenMapper.updateRefreshToken(existing);
        }
    }
}

