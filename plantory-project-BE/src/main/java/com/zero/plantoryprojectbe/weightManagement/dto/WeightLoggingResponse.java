package com.zero.plantoryprojectbe.weightManagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "최신 가중치 조회 응답 DTO")
public class WeightLoggingResponse {

    @Schema(description = "검색 가중치", example = "5.0")
    private BigDecimal searchWeight;

    @Schema(description = "질문 가중치", example = "3.0")
    private BigDecimal questionWeight;
}
