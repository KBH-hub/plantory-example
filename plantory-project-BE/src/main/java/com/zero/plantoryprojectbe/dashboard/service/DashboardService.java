package com.zero.plantoryprojectbe.dashboard.service;

import com.zero.plantoryprojectbe.dashboard.dto.DashboardSummaryResponse;
import com.zero.plantoryprojectbe.dashboard.dto.RecommendedSharingResponse;
import com.zero.plantoryprojectbe.dashboard.dto.TodayDiaryResponse;
import com.zero.plantoryprojectbe.dashboard.dto.TodayWateringResponse;

import java.util.List;

public interface DashboardService {
    int countMyPlants(Long memberId);
    int countTodayWatering(Long memberId);
    int countCareNeededPlants(Long memberId);
    List<RecommendedSharingResponse> getRecommendedSharingList();
    List<TodayWateringResponse> getTodayWatering(Long memberId);
    List<TodayDiaryResponse> getTodayDiary(Long memberId);
    DashboardSummaryResponse getDashboardSummary(Long memberId);
}
