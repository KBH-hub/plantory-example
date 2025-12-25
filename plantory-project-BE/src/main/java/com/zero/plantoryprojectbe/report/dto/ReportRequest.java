package com.zero.plantoryprojectbe.report.dto;

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
@Schema(description = "신고 등록 요청 DTO")
public class ReportRequest {

    @Schema(description = "신고 ID (서버에서 세팅)", example = "1")
    private Long reportId;

    @Schema(description = "처리 관리자 ID (서버에서 세팅)", example = "1")
    private Long adminId;

    @Schema(description = "신고자 회원 ID (서버에서 세팅)", example = "10")
    private Long reporterId;

    @Schema(description = "신고 대상 회원 ID", example = "25")
    private Long targetMemberId;

    @Schema(description = "신고 내용", example = "부적절한 언어 사용")
    private String content;

    @Schema(description = "신고 상태 (서버에서 관리)", example = "PENDING")
    private String status;

    @Schema(description = "신고 생성일시 (서버에서 세팅)", example = "2024-06-01T10:30:00")
    private Date createdAt;

    @Schema(description = "관리자 메모 (관리자 전용)", example = "경고 조치 예정")
    private String adminMemo;

    @Schema(description = "삭제 여부 플래그 (서버에서 세팅)", example = "null")
    private Date delFlag;
}
