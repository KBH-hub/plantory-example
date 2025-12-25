package com.zero.plantoryprojectbe.dashboard.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "추천 나눔 정보")
public class RecommendedSharingResponse {

    @Schema(description = "나눔 ID", example = "10")
    private Long sharingId;

    @Schema(description = "나눔 제목", example = "몬스테라 나눔합니다")
    private String title;

    @Schema(description = "나눔 상태", example = "OPEN")
    private String status;

    @Schema(description = "작성일시", example = "2025-12-23T12:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "관심 수", example = "12")
    private Integer interestNum;

    @Schema(description = "댓글 수", example = "3")
    private Integer commentCount;

    @Schema(description = "대표 이미지 URL", example = "https://storage.googleapis.com/plantory/images/2025/12/12/d3260843-6ca3-4f17-ac9a-4226b5e59e06-jejuOrangeCat_300_300.jpg")
    private String fileUrl;
}
