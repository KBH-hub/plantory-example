package com.zero.plantoryprojectbe.auth.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zero.plantoryprojectbe.member.Role;
import com.zero.plantoryprojectbe.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Schema(description = "회원 정보 조회 응답")
public class MemberInfoResponse {

    @Schema(description = "회원 ID", example = "8")
    private final Long memberId;

    @Schema(description = "회원 아이디(로그인 ID)", example = "user_alice")
    private final String membername;

    @Schema(description = "닉네임", example = "user_alice")
    private final String nickname;

    @Schema(description = "전화번호", example = "010-1234-5678")
    private final String phone;

    @Schema(description = "주소", example = "서울특별시 금천구")
    private final String address;

    @Schema(description = "나눔 평점", example = "5.5")
    private final BigDecimal sharingRate;

    @Schema(description = "기술 숙련도 점수", example = "9.0")
    private final BigDecimal skillRate;

    @Schema(description = "관리 숙련도 점수", example = "7.0")
    private final BigDecimal managementRate;

    @Schema(description = "회원 프로필 이미지", example = "https://storage.googleapis.com/plantory/images/2025/12/12/d3260843-6ca3-4f17-ac9a-4226b5e59e06-jejuOrangeCat_300_300.jpg")
    private String profileImageUrl;

    @Schema(description = "회원 권한", example = "USER")
    private final Role role;



    @Schema(
            description = "정지 해제 예정일 (없으면 null)",
            example = "2025-12-31 23:59:59"
    )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime stopDay;

    private MemberInfoResponse(
            Long memberId,
            String membername,
            String nickname,
            String phone,
            String address,
            BigDecimal sharingRate,
            BigDecimal skillRate,
            BigDecimal managementRate,
            String  profileImageUrl,
            Role role,
            LocalDateTime stopDay
    ) {
        this.memberId = memberId;
        this.membername = membername;
        this.nickname = nickname;
        this.phone = phone;
        this.address = address;
        this.sharingRate = sharingRate;
        this.skillRate = skillRate;
        this.managementRate = managementRate;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
        this.stopDay = stopDay;
    }

    public static MemberInfoResponse from(Member member, String profileImageUrl) {
        return new MemberInfoResponse(
                member.getMemberId(),
                member.getMembername(),
                member.getNickname(),
                member.getPhone(),
                member.getAddress(),
                member.getSharingRate(),
                member.getSkillRate(),
                member.getManagementRate(),
                profileImageUrl,
                member.getRole(),
                member.getStopDay()
        );
    }

}