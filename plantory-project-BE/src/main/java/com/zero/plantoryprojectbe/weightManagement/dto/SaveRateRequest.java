package com.zero.plantoryprojectbe.weightManagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "관리/숙련도 비율 저장 요청 DTO")
public class SaveRateRequest {

    @Schema(description = "회원 ID (서버에서 세팅)", example = "10")
    private Long memberId;

    @Schema(description = "초기 숙련도 비율", example = "0.20")
    private BigDecimal initialSkillRate;

    @Schema(description = "숙련도 1단계 비율", example = "0.30")
    private BigDecimal skillRateGrade1;

    @Schema(description = "숙련도 2단계 비율", example = "0.40")
    private BigDecimal skillRateGrade2;

    @Schema(description = "숙련도 3단계 비율", example = "0.50")
    private BigDecimal skillRateGrade3;

    @Schema(description = "숙련도 4단계 비율", example = "0.60")
    private BigDecimal skillRateGrade4;

    @Schema(description = "초기 관리 비율", example = "0.25")
    private BigDecimal initialManagementRate;

    @Schema(description = "관리 1단계 비율", example = "0.35")
    private BigDecimal managementRateGrade1;

    @Schema(description = "관리 2단계 비율", example = "0.45")
    private BigDecimal managementRateGrade2;

    @Schema(description = "관리 3단계 비율", example = "0.55")
    private BigDecimal managementRateGrade3;
}
