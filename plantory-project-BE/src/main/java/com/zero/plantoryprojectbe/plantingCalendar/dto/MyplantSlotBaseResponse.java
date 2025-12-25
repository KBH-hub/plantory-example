package com.zero.plantoryprojectbe.plantingCalendar.dto;

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
@Schema(description = "식물 기본 주기 정보")
public class MyplantSlotBaseResponse {

    @Schema(description = "내 식물 ID", example = "5")
    private Long myplantId;

    @Schema(description = "시작 일시", example = "2025-01-01T00:00:00")
    private LocalDateTime startAt;

    @Schema(description = "종료 일시", example = "2025-12-31T23:59:59")
    private LocalDateTime endDate;

    @Schema(description = "물 주기 간격(일)", example = "3")
    private Integer interval;

    @Schema(description = "회원 ID", example = "8")
    private Long memberId;

    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phone;

    @Schema(description = "식물 이름", example = "몬스테라")
    private String name;
}
