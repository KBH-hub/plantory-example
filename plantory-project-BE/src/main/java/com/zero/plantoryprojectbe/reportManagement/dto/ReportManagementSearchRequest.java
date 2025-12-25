package com.zero.plantoryprojectbe.reportManagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "신고 목록 검색 요청 DTO")
public class ReportManagementSearchRequest {

    @Schema(description = "검색 키워드(선택)", example = "욕설")
    private String keyword;

    @Schema(description = "상태 필터(선택)", example = "PENDING")
    private String status;

    @Schema(description = "조회 개수(선택)", example = "10")
    private Integer limit;

    @Schema(description = "오프셋(선택)", example = "0")
    private Integer offset;
}
