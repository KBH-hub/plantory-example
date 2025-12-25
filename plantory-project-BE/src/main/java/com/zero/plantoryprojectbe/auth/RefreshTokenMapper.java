package com.zero.plantoryprojectbe.auth;

import com.zero.plantoryprojectbe.auth.dto.RefreshTokenRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RefreshTokenMapper {

    int insertRefreshToken(RefreshTokenRequest refreshTokenRequest);
    int updateRefreshToken(RefreshTokenRequest refreshTokenRequest);
    int deleteByMemberId(Long memberId);
    RefreshTokenRequest selectByMemberId(Long memberId);
    RefreshTokenRequest selectByTokenHash(String tokenHash);
    int deleteByTokenHash(String tokenHash);
}

