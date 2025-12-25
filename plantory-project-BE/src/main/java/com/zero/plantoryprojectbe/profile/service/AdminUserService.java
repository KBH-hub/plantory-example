package com.zero.plantoryprojectbe.profile.service;

import com.zero.plantoryprojectbe.auth.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final RefreshTokenService refreshTokenService;

    public void forceLogout(Long memberId) {
        refreshTokenService.deleteByUserId(memberId);
    }
}

