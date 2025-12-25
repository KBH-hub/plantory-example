package com.zero.plantoryprojectbe.plantingCalendar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "일지 작성용 내 식물 정보")
public class MyPlantDiaryResponse {

    @Schema(description = "내 식물 ID", example = "5")
    private Long myplantId;

    @Schema(description = "식물 이름", example = "몬스테라")
    private String name;
}
