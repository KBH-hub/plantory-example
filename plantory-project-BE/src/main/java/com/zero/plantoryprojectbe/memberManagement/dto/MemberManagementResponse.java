package com.zero.plantoryprojectbe.memberManagement.dto;

import com.zero.plantoryprojectbe.global.plantoryEnum.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "회원 관리 조회 응답")
public class MemberManagementResponse {

    @Schema(description = "회원 ID", example = "8")
    private Long memberId;

    @Schema(description = "회원 아이디", example = "user_bob")
    private String membername;

//    @Schema(description = "비밀번호(암호화)", example = "********")
//    private String password;

    @Schema(description = "닉네임", example = "밥")
    private String nickname;

    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phone;

    @Schema(description = "주소", example = "서울특별시 금천구")
    private String address;

    @Schema(description = "나눔 평점", example = "3.5")
    private Integer sharingRate;

    @Schema(description = "관리 숙련도 평점", example = "2.0")
    private Integer skillRate;

    @Schema(description = "관리 요구도 평점", example = "5.0")
    private Integer managementRate;

    @Schema(description = "회원 권한", example = "ROLE_USER")
    private Role role;

    @Schema(description = "정지 해제 예정일", example = "2025-12-31T23:59:59")
    private LocalDateTime stopDay;

    @Schema(description = "삭제 일시", example = "2025-12-20T10:00:00")
    private LocalDateTime delFlag;

    @Schema(description = "가입 일시", example = "2025-01-10T09:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "알림 수신 여부", example = "1/0이면 on/off")
    private Integer noticeEnabled;

    @Schema(description = "전체 회원 수", example = "120")
    private Integer totalCount;
}
