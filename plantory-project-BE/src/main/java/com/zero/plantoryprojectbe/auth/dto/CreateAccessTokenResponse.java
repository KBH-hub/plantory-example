package com.zero.plantoryprojectbe.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(description = "Access Token 재발급 응답")
public class CreateAccessTokenResponse {

    @Schema(
            description = "새로 발급된 Access Token",
            example = "RSA대표적 공개키(비대칭키) 알고리즘 값..."
    )
    private String accessToken;
}
