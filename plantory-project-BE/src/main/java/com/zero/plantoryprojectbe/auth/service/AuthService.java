package com.zero.plantoryprojectbe.auth.service;

import com.zero.plantoryprojectbe.member.MemberMapper;
import com.zero.plantoryprojectbe.global.security.MemberDetail;
import com.zero.plantoryprojectbe.auth.dto.LoginRequest;
import com.zero.plantoryprojectbe.global.security.jwt.TokenProvider;
import com.zero.plantoryprojectbe.profile.dto.MemberResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final MemberMapper memberMapper;

    public Map<String, String> login(
            LoginRequest request,
            HttpServletResponse response
    ) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getMembername(),
                                request.getPassword()
                        )
                );

        MemberDetail memberDetail =
                (MemberDetail) authentication.getPrincipal();

        Long memberId = memberDetail.memberResponse().getMemberId();

        String accessToken =
                tokenProvider.createAccessToken(memberId.toString());

        String refreshToken =
                tokenProvider.createRefreshToken(memberId.toString());

        refreshTokenService.save(memberId, refreshToken);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 5);
//        cookie.setMaxAge(60 * 60 * 24 * 14);
        cookie.setSecure(false);
        cookie.setAttribute("SameSite", "Lax");  // 배표시 Lax --> None
        response.addCookie(cookie);

        return Map.of("accessToken", accessToken);
    }

    public MemberResponse findMemberById(Long memberId) {
        MemberResponse member = memberMapper.selectByMemberId(memberId);

        if (member == null) {
            throw new IllegalArgumentException("존재하지 않는 회원");
        }

        return member;
    }
}


