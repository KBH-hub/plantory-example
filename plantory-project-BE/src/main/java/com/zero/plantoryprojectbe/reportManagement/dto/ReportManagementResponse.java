package com.zero.plantoryprojectbe.reportManagement.dto;

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
@Schema(description = "신고 목록 단건 응답 DTO")
public class ReportManagementResponse {

    @Schema(description = "신고 ID", example = "1")
    private Long reportId;

    @Schema(description = "처리 관리자 ID", example = "2")
    private Long adminId;

    @Schema(description = "신고자 회원 ID", example = "10")
    private Long reporterId;

    @Schema(description = "신고 대상 회원 ID", example = "25")
    private Long targetMemberId;

    @Schema(description = "신고 내용", example = "부적절한 언어 사용")
    private String content;

    @Schema(description = "신고 상태", example = "PENDING")
    private String status;

    @Schema(description = "생성일시", example = "2024-06-01T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "관리자 메모", example = "확인 중")
    private String adminMemo;

    @Schema(description = "삭제 여부 플래그(소프트 삭제)", example = "null")
    private LocalDateTime delFlag;
}
