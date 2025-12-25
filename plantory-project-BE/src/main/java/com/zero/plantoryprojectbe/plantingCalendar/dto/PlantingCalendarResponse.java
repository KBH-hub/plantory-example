package com.zero.plantoryprojectbe.plantingCalendar.dto;

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
@Schema(description = "캘린더 일정 응답")
public class PlantingCalendarResponse {

    @Schema(description = "물주기 ID", example = "10")
    private Long wateringId;

    @Schema(description = "내 식물 ID", example = "5")
    private Long myplantId;

    @Schema(description = "일지 ID", example = "21")
    private Long diaryId;

    @Schema(description = "회원 ID", example = "8")
    private Long memberId;

    @Schema(description = "식물 이름", example = "몬스테라")
    private String name;

    @Schema(description = "내용", example = "물 주기")
    private String content;

    @Schema(description = "메모", example = "상태 양호")
    private String memo;

    @Schema(description = "일정 타입", example = "WATERING")
    private String type;

    @Schema(description = "생성 일시", example = "2025-01-10T10:30:00")
    private Date createdAt;

    @Schema(description = "체크 일시", example = "2025-01-10T11:00:00")
    private Date checkFlag;

    @Schema(description = "기준 날짜", example = "2025-01-10T00:00:00")
    private Date dateAt;
}
