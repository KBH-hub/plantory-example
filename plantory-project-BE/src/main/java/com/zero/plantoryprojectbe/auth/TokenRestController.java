package com.zero.plantoryprojectbe.auth;

import com.zero.plantoryprojectbe.auth.dto.CreateAccessTokenRequest;
import com.zero.plantoryprojectbe.auth.dto.CreateAccessTokenResponse;
import com.zero.plantoryprojectbe.auth.service.RefreshTokenService;
import com.zero.plantoryprojectbe.auth.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RequiredArgsConstructor
@RestController
@Tag(name = "Token", description = "토큰 발급/삭제 API")
public class TokenRestController {

    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    @Operation(
            summary = "Access Token 재발급(Refresh Token 직접 전달)",
            description = "refreshToken을 request body로 전달받아 새 Access Token을 발급합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "재발급 성공",
                    content = @Content(schema = @Schema(implementation = CreateAccessTokenResponse.class))),
            @ApiResponse(responseCode = "401", description = "Refresh Token이 유효하지 않음"),
            @ApiResponse(responseCode = "400", description = "요청값 오류")
    })
    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(
            @RequestBody CreateAccessTokenRequest request
    ) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }

    @Operation(
            summary = "Refresh Token 삭제(로그아웃 보조)",
            description = "인증된 사용자의 refresh token을 서버 저장소에서 삭제합니다."
    )
    @SecurityRequirement(name = "bearerAuth") // 전역이면 생략 가능
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요(JWT 누락/만료)"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @DeleteMapping("/api/refreshToken")
    public ResponseEntity<Void> deleteRefreshToken(Authentication authentication) {

        Long userId = Long.valueOf(authentication.getName());
        refreshTokenService.deleteByUserId(userId);

        return ResponseEntity.ok().build();
    }
}