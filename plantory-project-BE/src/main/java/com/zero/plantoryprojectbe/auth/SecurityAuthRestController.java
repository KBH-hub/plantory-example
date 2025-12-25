package com.zero.plantoryprojectbe.auth;

import com.zero.plantoryprojectbe.auth.dto.AuthMeResponse;
import com.zero.plantoryprojectbe.auth.dto.AuthUserResponse;
import com.zero.plantoryprojectbe.auth.dto.LoginRequest;
import com.zero.plantoryprojectbe.auth.service.AuthService;
import com.zero.plantoryprojectbe.auth.dto.RefreshTokenRequest;
import com.zero.plantoryprojectbe.global.security.jwt.TokenProvider;
import com.zero.plantoryprojectbe.auth.service.RefreshTokenService;
import com.zero.plantoryprojectbe.profile.dto.MemberResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "인증/토큰 발급 및 재발급 API")
public class SecurityAuthRestController {

    private final RefreshTokenService refreshTokenService;
    private final AuthService authService;
    private final TokenProvider tokenProvider;

    @Operation(
            summary = "로그인",
            description = "이메일/비밀번호로 로그인합니다. 성공 시 Access Token을 응답으로 받고, Refresh Token은 쿠키(refreshToken)로 설정됩니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "로그인 실패(자격 증명 불일치)"),
            @ApiResponse(responseCode = "400", description = "요청값 오류")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "로그인 요청",
                    required = true,
                    content = @Content(schema = @Schema(implementation = LoginRequest.class))
            )
            LoginRequest loginRequest,
            HttpServletResponse httpServletResponse
    ) {
        return ResponseEntity.ok(authService.login(loginRequest, httpServletResponse));
    }

    @Operation(
            summary = "로그아웃",
            description = "Refresh Token 쿠키를 삭제하고(만료), 서버 저장소에서도 refresh token을 제거합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "401", description = "Refresh Token 쿠키가 없거나 유효하지 않음(이미 로그아웃 상태일 수 있음)")
    })
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @Parameter(description = "리프레시 토큰 쿠키 값", required = false)
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response
    ) {
        if (refreshToken != null) {
            refreshTokenService.deleteByToken(refreshToken);
        }

        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Access Token 재발급",
            description = "Refresh Token 쿠키를 기반으로 새로운 Access Token을 발급합니다. (Refresh Token이 없거나 유효하지 않으면 401)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "재발급 성공"),
            @ApiResponse(responseCode = "401", description = "Refresh Token 없음/만료/유효하지 않음"),
            @ApiResponse(responseCode = "404", description = "회원 정보 없음")
    })
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(
            @Parameter(description = "리프레시 토큰 쿠키 값", required = false)
            @CookieValue(name = "refreshToken", required = false) String refreshToken
    ) {
        if (refreshToken == null) {
            return ResponseEntity.status(401).build();
        }

        RefreshTokenRequest rt = refreshTokenService.findByRefreshToken(refreshToken);
        Long memberId = rt.getMemberId();

        MemberResponse member = authService.findMemberById(memberId);

        String newAccessToken = tokenProvider.createAccessToken(String.valueOf(memberId));

        return ResponseEntity.ok(Map.of(
                "accessToken", newAccessToken,
                "user", Map.of(
                        "memberId", member.getMemberId(),
                        "membername", member.getMembername(),
                        "role", member.getRole()
                )
        ));
    }

    @Operation(
            summary = "내 정보 조회 + Access Token 재발급",
            description = "Refresh Token 쿠키 기반으로 사용자 정보를 조회하고, 새 Access Token을 함께 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = AuthMeResponse.class))),
            @ApiResponse(responseCode = "401", description = "Refresh Token 없음/만료/유효하지 않음"),
            @ApiResponse(responseCode = "404", description = "회원 정보 없음")
    })
    @GetMapping("/me")
    public ResponseEntity<AuthMeResponse> me(
            @Parameter(description = "리프레시 토큰 쿠키 값", required = false)
            @CookieValue(name = "refreshToken", required = false) String refreshToken
    ) {
        if (refreshToken == null) {
            return ResponseEntity.status(401).build();
        }

        RefreshTokenRequest rt;
        try {
            rt = refreshTokenService.findByRefreshToken(refreshToken);
        } catch (Exception e) {
            log.warn("Invalid refresh token", e);
            return ResponseEntity.status(401).build();
        }

        Long memberId = rt.getMemberId();
        MemberResponse member = authService.findMemberById(memberId);

        if (member == null) {
            return ResponseEntity.status(401).build();
        }

        String accessToken = tokenProvider.createAccessToken(String.valueOf(memberId));

        return ResponseEntity.ok(
                AuthMeResponse.builder()
                        .user(
                                AuthUserResponse.builder()
                                        .memberId(member.getMemberId())
                                        .membername(member.getMembername())
                                        .nickname(member.getNickname())
                                        .phone(member.getPhone())
                                        .address(member.getAddress())
                                        .sharingRate(member.getSharingRate())
                                        .skillRate(member.getSkillRate())
                                        .managementRate(member.getManagementRate())
                                        .role(member.getRole())
                                        .stopDay(member.getStopDay())
                                        .build()
                        )
                        .accessToken(accessToken)
                        .build()
        );
    }
}