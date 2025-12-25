package com.zero.plantoryprojectbe.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Access Token 재발급 요청")
public class CreateAccessTokenRequest {

    @Schema(
            description = "Refresh Token 문자열",
            example = "RSA대표적 공개키(비대칭키) 알고리즘 값..."
    )
    private String refreshToken;
}
