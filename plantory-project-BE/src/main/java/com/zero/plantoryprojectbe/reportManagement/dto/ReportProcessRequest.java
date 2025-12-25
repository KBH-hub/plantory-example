package com.zero.plantoryprojectbe.reportManagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "신고 처리 요청 DTO")
public class ReportProcessRequest {

    @Schema(description = "신고 대상 회원 ID", example = "25")
    private Long targetMemberId;

    @Schema(description = "관리자 메모", example = "7일 정지 조치")
    private String adminMemo;

    @Schema(description = "정지 일수", example = "7")
    private int stopDays;
}
