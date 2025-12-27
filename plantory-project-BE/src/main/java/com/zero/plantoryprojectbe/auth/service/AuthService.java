package com.zero.plantoryprojectbe.auth.service;

import com.zero.plantoryprojectbe.auth.dto.MemberInfoResponse;
import com.zero.plantoryprojectbe.auth.dto.LoginRequest;
import com.zero.plantoryprojectbe.global.security.MemberPrincipal;
import com.zero.plantoryprojectbe.global.security.jwt.TokenProvider;
import com.zero.plantoryprojectbe.member.Member;
import com.zero.plantoryprojectbe.member.MemberImage;
import com.zero.plantoryprojectbe.member.MemberImageRepository;
import com.zero.plantoryprojectbe.member.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.zero.plantoryprojectbe.global.plantoryEnum.ImageTargetType.PROFILE;


@Log4j2
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final MemberRepository memberRepository;
    private final MemberImageRepository memberImageRepository;

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

        MemberPrincipal principal = (MemberPrincipal) authentication.getPrincipal();
        Long memberId = principal.getMemberId();

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
        cookie.setAttribute("SameSite", "Lax"); // HTTPS일때는 None으로 변경
        response.addCookie(cookie);

        return Map.of("accessToken", accessToken);
    }

    public MemberInfoResponse findMemberInfo(Long memberId) {
        Member member = memberRepository
                .findByMemberIdAndDelFlagIsNull(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원"));

        String profileImageUrl = memberImageRepository
                .findTop1ByMember_MemberIdAndTargetTypeAndDelFlagIsNullOrderByCreatedAtDesc(memberId, "PROFILE")
                .map(MemberImage::getFileUrl)
                .orElse(null);

        return MemberInfoResponse.from(member, profileImageUrl);
    }

}


