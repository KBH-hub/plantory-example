package com.zero.plantoryprojectbe.weightManagement.service;

import com.zero.plantoryprojectbe.weightManagement.dto.*;

import java.util.List;
import java.util.Map;

public interface WeightManagementService {
    List<WeightManagementResponse> getMemberWeightList(String keyword, int limit, int offset, String range);
    void saveWeights(Long memberId, SaveWeightRequest saveWeightRequest);
    WeightLoggingResponse getLatestWeights();
    Map<Long, Integer> getPlantsNeedingAttention();
    RateResponse getRate();
    void saveRate(Long memberId, SaveRateRequest saveRateRequest);
}