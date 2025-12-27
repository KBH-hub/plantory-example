package com.zero.plantoryprojectbe.auth.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zero.plantoryprojectbe.member.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "인증된 사용자 정보")
public class AuthUserResponse {

    @Schema(description = "회원 ID", example = "8")
    private Long memberId;

    @Schema(description = "회원 아이디(로그인 ID)", example = "user_alice")
    private String membername;

    @Schema(description = "닉네임", example = "기무")
    private String nickname;

    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phone;

    @Schema(description = "주소", example = "서울특별시 금천구")
    private String address;

    @Schema(description = "나눔 평점", example = "5.5")
    private BigDecimal sharingRate;

    @Schema(description = "기술 숙련도 점수", example = "9.0")
    private BigDecimal skillRate;

    @Schema(description = "관리 숙련도 점수", example = "7.0")
    private BigDecimal managementRate;

    @Schema(description = "회원 권한", example = "ROLE_USER")
    private Role role;

    @Schema(description = "회원 프로필 이미지", example = "https://storage.googleapis.com/plantory/images/2025/12/12/d3260843-6ca3-4f17-ac9a-4226b5e59e06-jejuOrangeCat_300_300.jpg")
    private String profileImageUrl;

    @Schema(
            description = "정지 해제 예정일 (없으면 null)",
            example = "2025-12-31 23:59:59"
    )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime stopDay;
}
