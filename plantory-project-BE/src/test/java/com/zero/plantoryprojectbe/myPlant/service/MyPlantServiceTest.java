package com.zero.plantoryprojectbe.myPlant.service;

import com.zero.plantoryprojectbe.myPlant.dto.MyPlantRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
@Transactional
class MyPlantServiceTest {

    @Autowired
    private  MyPlantService myPlantService;

    @Test
    @DisplayName("내 식물 조회")
    void getMyPlantList() {
        Long memberId = 1L;
        String name = "";
        int limit = 10;
        int offset = 0;

        var list = myPlantService.getMyPlantList(memberId, name, limit, offset);

        log.info("[조회] memberId={}, name='{}', limit={}, offset={}, resultSize={}",
                memberId, name, limit, offset, list.size());

        for (var r : list) {
            log.info("[조회 row] myplantId={}, memberId={}, name={}, createdAt={}, delFlag={}, totalCount={}",
                    r.getMyplantId(), r.getMemberId(), r.getName(), r.getCreatedAt(), r.getDelFlag(), r.getTotalCount());
        }
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
    @DisplayName("내 식물 수정 처리")
    void updateMyPlant() throws Exception {
        Long memberId = 1L;

        MyPlantRequest req = new MyPlantRequest();
        req.setMyplantId(1L);
        req.setName("몬스테라_서비스수정");
        req.setType("관엽식물_서비스수정");
        req.setStartAt(LocalDateTime.of(2025, 12, 1, 0, 0));
        req.setEndDate(LocalDateTime.of(2025, 12, 31, 0, 0));
        req.setInterval(9);
        req.setSoil("배양토_서비스수정");
        req.setTemperature("19~26℃");

        int result = myPlantService.updateMyPlant(req, null, null, memberId);

        log.info("[수정] result={}, myplantId={}", result, req.getMyplantId());
    }

    @Test
    @DisplayName("내 식물 삭제 처리")
    void removePlant() throws IOException {
        Long myplantId = 2L;

        int result = myPlantService.removePlant(myplantId, null);

        log.info("[삭제] result={}, myplantId={}", result, myplantId);
    }
}
