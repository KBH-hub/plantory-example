package com.zero.plantoryprojectbe.weightManagement;

import com.zero.plantoryprojectbe.weightManagement.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface WeightManagementMapper {

    List<WeightManagementResponse> selectWeightManagementList(
            @Param("keyword") String keyword,
            @Param("limit") Integer limit,
            @Param("offset") Integer offset,
            @Param("startDate") LocalDateTime startDate
    );

    int insertWeights(SaveWeightRequest saveWeightRequest);

    WeightLoggingResponse selectLatestWeights();

    List<Map<String, Object>> selectPlantsNeedingAttention();

    RateResponse selectLatestRateGrades();

    int insertSkillRateGrade(SaveRateRequest saveRateRequest);
    int insertManagementRateGrade(SaveRateRequest saveRateRequest);
}

