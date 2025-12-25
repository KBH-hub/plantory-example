package com.zero.plantoryprojectbe.reportManagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "신고 목록 페이징 응답 DTO")
public class ReportManagementPageResponse {

    @Schema(description = "전체 신고 수", example = "125")
    private int totalCount;

    @Schema(description = "신고 목록")
    private List<ReportManagementResponse> list;
}
