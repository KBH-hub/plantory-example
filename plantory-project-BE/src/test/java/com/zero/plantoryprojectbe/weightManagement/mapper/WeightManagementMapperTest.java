package com.zero.plantoryprojectbe.weightManagement.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zero.plantoryprojectbe.weightManagement.WeightManagementMapper;
import com.zero.plantoryprojectbe.weightManagement.dto.SaveWeightRequest;
import com.zero.plantoryprojectbe.weightManagement.dto.WeightManagementResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
@Transactional
class WeightManagementMapperTest {

    @Autowired
    private WeightManagementMapper weightManagementMapper;

    @Test
    @DisplayName("회원 가중치 리스트 조회 테스트")
    void selectWeightManagementList() throws Exception {
        List<WeightManagementResponse> list =
                weightManagementMapper.selectWeightManagementList("관", 10, 0, LocalDateTime.now());

        String json = new ObjectMapper().writerWithDefaultPrettyPrinter()
                .writeValueAsString(list);

        log.info("결과 JSON:\n{}", json);
    }

    @Test
    @DisplayName("회원 가중치 등록 테스트")
    void insertWeights() {
        SaveWeightRequest request = new SaveWeightRequest();
        request.setMemberId(1L);
        request.setSearchWeight(0.3);
        request.setQuestionWeight(0.7);
        int result = weightManagementMapper.insertWeights(request);
        assertEquals(1, result);
    }

    @Test
    @DisplayName("회원 최신 가중치 조회 테스트")
    void selectLatestWeights() {
        log.info("selectLatestWeights 로그 결과:" + weightManagementMapper.selectLatestWeights());
    }
}
