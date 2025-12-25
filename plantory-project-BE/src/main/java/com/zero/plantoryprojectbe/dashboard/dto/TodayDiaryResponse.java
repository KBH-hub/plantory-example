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
@Schema(description = "오늘 작성된 일지 정보")
public class TodayDiaryResponse {

    @Schema(description = "일지 ID", example = "21")
    private Long diaryId;

    @Schema(description = "내 식물 ID", example = "5")
    private Long myplantId;

    @Schema(description = "내 식물 이름", example = "몬스테라")
    private String myplantName;

    @Schema(description = "활동 내용", example = "물 주기")
    private String activity;

    @Schema(description = "식물 상태", example = "GOOD")
    private String state;

    @Schema(description = "메모", example = "잎 상태가 좋아짐")
    private String memo;

    @Schema(description = "작성일시", example = "2025-12-23 09:15:00")
    private String createdAt;

    @Schema(description = "이미지 URL", example = "https://storage.googleapis.com/plantory/images/2025/12/12/9739ee9d-b54d-4d2a-9313-8d9a5bab2191-images.jpg")
    private String fileUrl;
}
