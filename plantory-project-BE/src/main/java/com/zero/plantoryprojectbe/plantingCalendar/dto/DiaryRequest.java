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
@Schema(description = "일지 등록/수정 요청")
public class DiaryRequest {

    @Schema(description = "일지 ID", example = "21")
    Long diaryId;

    @Schema(description = "내 식물 ID", example = "5")
    Long myplantId;

    @Schema(description = "활동 내용", example = "물 주기")
    String activity;

    @Schema(description = "식물 상태", example = "GOOD")
    String state;

    @Schema(description = "메모", example = "잎이 더 푸르러짐")
    String memo;

    @Schema(description = "작성 일시", example = "2025-01-10T10:30:00")
    LocalDateTime createdAt;
}
