package com.zero.plantoryprojectbe.reportManagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "신고 상세 조회 응답 DTO")
public class ReportManagementDetailResponse {

    @Schema(description = "신고 ID", example = "1")
    private Long reportId;

    @Schema(description = "처리 관리자 ID", example = "2")
    private Long adminId;

    @Schema(description = "신고자 회원 ID", example = "10")
    private Long reporterId;

    @Schema(description = "신고 대상 회원 ID", example = "25")
    private Long targetMemberId;

    @Schema(description = "신고자 닉네임", example = "식물집사")
    private String reporterName;

    @Schema(description = "신고 대상 닉네임", example = "초보집사")
    private String targetName;

    @Schema(description = "관리자 이름", example = "admin")
    private String adminName;

    @Schema(description = "신고 내용", example = "부적절한 언어 사용")
    private String content;

    @Schema(description = "신고 상태", example = "PENDING")
    private String status;

    @Schema(description = "신고 생성일시", example = "2024-06-01T10:30:00")
    private Date createdAt;

    @Schema(description = "관리자 메모", example = "3일 정지 조치")
    private String adminMemo;
}
