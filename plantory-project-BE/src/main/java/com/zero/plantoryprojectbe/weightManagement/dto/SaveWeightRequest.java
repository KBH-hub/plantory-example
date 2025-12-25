package com.zero.plantoryprojectbe.weightManagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "가중치 저장 요청 DTO")
public class SaveWeightRequest {

    @Schema(description = "회원 ID (서버에서 세팅)", example = "10")
    private Long memberId;

    @Schema(description = "검색 가중치", example = "5.0")
    private Double searchWeight;

    @Schema(description = "질문 가중치", example = "3.0")
    private Double questionWeight;
}
