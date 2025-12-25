package com.zero.plantoryprojectbe.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Refresh Token 정보(내부 처리용)")
public class RefreshTokenRequest {

    @Schema(description = "리프레시 토큰 ID", example = "1")
    private Long refreshTokenId;

    @Schema(description = "회원 ID", example = "8")
    private Long memberId;

    @Schema(description = "리프레시 토큰 해시값")
    private String tokenHash;
}
