package com.zero.plantoryprojectbe.dashboard.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "오늘 물 주기 정보")
public class TodayWateringResponse {

    @Schema(description = "식물 이름", example = "스투키")
    private String name;

    @Schema(description = "물 주기 주기(일)", example = "3")
    private Integer interval;
}
