package com.zero.plantoryprojectbe.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "내 정보 조회 응답")
public class AuthMeResponse {

    @Schema(description = "로그인한 사용자 정보")
    private AuthUserResponse user;

    @Schema(
            description = "새로 발급된 Access Token (Authorization 헤더에 Bearer로 사용)",
            example = "RSA대표적 공개키(비대칭키) 알고리즘 값..."
    )
    private String accessToken;
}
