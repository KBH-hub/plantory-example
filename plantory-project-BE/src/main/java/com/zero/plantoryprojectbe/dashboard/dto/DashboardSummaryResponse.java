package com.zero.plantoryprojectbe.dashboard.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "대시보드 응답")
public class DashboardSummaryResponse {

    @Schema(description = "내가 관리 중인 식물 수", example = "5")
    private int myPlantsCount;

    @Schema(description = "오늘 물 주기가 필요한 식물 수", example = "2")
    private int todayWateringCount;

    @Schema(description = "주의 관리가 필요한 식물 수", example = "1")
    private int careNeededCount;

    @Schema(description = "추천 나눔 목록")
    private List<RecommendedSharingResponse> recommendeds;

    @Schema(description = "오늘 물 주기 목록")
    private List<TodayWateringResponse> waterings;

    @Schema(description = "오늘 작성된 일지 목록")
    private List<TodayDiaryResponse> diaries;
}
