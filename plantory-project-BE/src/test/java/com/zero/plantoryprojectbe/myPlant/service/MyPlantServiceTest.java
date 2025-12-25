package com.zero.plantoryprojectbe.myPlant.service;

import com.zero.plantoryprojectbe.myPlant.dto.MyPlantRequest;
import com.zero.plantoryprojectbe.myPlant.dto.MyPlantResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Transactional
@Slf4j
class MyPlantServiceTest {

    @Autowired
    MyPlantService myPlantService;

    @Test
    @DisplayName("내 식물 리스트 조회")
    void getMyPlantListTest() {
        Long memberId = 1L;
        String name = "";
        int limit = 10;
        int offset = 0;

        List<MyPlantResponse> result = myPlantService.getMyPlantList(memberId, name, limit, offset);
        log.info("result={}", result);

        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("내 식물 등록 처리")
    void registerMyPlantTest() throws IOException {
        MyPlantRequest request = MyPlantRequest.builder()
                .memberId(1L)
                .name("테스트마이플랜트명")
                .type("테스트마이플랜트타입")
                .startAt(Timestamp.valueOf("2025-10-01 00:00:00").toLocalDateTime())
                .endDate(Timestamp.valueOf("2025-11-01 00:00:00").toLocalDateTime())
                .interval(28)
                .soil("테스트마이플랜트비료")
                .temperature("666~999℃")
                .build();

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "img1.png",
                "image/png",
                "image1".getBytes()
        );

        Long memberId = 1L;

        int result = myPlantService.registerMyPlant(request, file, memberId);
        log.info("등록 처리 건수: {}", result);

        assertEquals(2, result);
    }

    @Test
    @DisplayName("내 식물 등록 필수값 누락 실패 처리")
    void registerMyPlantFailTest() {
        MyPlantRequest request = MyPlantRequest.builder()
                .memberId(4L)
                .name("")
                .type("테스트마이플랜트타입")
                .startAt(Timestamp.valueOf("2025-10-01 00:00:00").toLocalDateTime())
                .endDate(Timestamp.valueOf("2025-11-01 00:00:00").toLocalDateTime())
                .interval(28)
                .soil("테스트마이플랜트비료")
                .temperature("666~999℃")
                .build();

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "img1.png",
                "image/png",
                "image1".getBytes()
        );

        Long memberId = 1L;

        boolean result;
        try {
            myPlantService.registerMyPlant(request, file, memberId);
            result = true;
        } catch (Exception e) {
            result = false;
        }

        assertFalse(result);
        log.info("등록 실패 처리 결과: {}", result);
    }

    @Test
    @DisplayName("내 식물 수정 처리")
    void updateMyPlantTest() throws IOException {
        MyPlantRequest request = MyPlantRequest.builder()
                .memberId(1L)
                .myplantId(1L)
                .name("수정테스트식물")
                .type("수정테스트마이플랜트타입")
                .startAt(Timestamp.valueOf("2025-10-01 00:00:00").toLocalDateTime())
                .endDate(Timestamp.valueOf("2025-11-01 00:00:00").toLocalDateTime())
                .interval(28)
                .soil("수정테스트마이플랜트비료")
                .temperature("666~999℃")
                .build();

        Long delFileTargetId = 1L;

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "img1.png",
                "image/png",
                "image1".getBytes()
        );

        Long memberId = 1L;

        int result = myPlantService.updateMyPlant(request, delFileTargetId, file, memberId);
        log.info("수정 처리 건수: {}", result);

        // update + 이미지 삭제 + 이미지 insert = 3
        assertEquals(3, result);
    }

    @Test
    @DisplayName("내 식물 삭제 처리")
    void removePlantTest() throws IOException {
        Long myPlantId = 1L;
        Long delFile = 1L; // 실제 존재하는 이미지 ID

        int result = myPlantService.removePlant(myPlantId, delFile);
        log.info("삭제 처리 건수: {}", result);

        assertEquals(2, result);
    }

}
