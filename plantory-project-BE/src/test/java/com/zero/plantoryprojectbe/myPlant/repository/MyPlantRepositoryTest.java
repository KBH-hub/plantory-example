package com.zero.plantoryprojectbe.myPlant.repository;

import com.zero.plantoryprojectbe.myPlant.MyPlant;
import com.zero.plantoryprojectbe.myPlant.MyPlantRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@SpringBootTest
@Transactional
class MyPlantRepositoryTest {

    @Autowired
    private MyPlantRepository myPlantRepository;

    @Test
    void selectMyPlantList() {
        Long memberId = 1L;
        String name = "";
        int page = 0;
        int size = 10;

        Pageable pageable = PageRequest.of(page, size);
        Page<MyPlant> result = myPlantRepository.selectMyPlantList(memberId, name, pageable);

        log.info("[조회] memberId={}, name='{}', total={}, contentSize={}",
                memberId, name, result.getTotalElements(), result.getContent().size());

        for (MyPlant p : result.getContent()) {
            log.info("[조회 row] myplantId={}, memberId={}, name={}, createdAt={}, delFlag={}",
                    p.getMyplantId(), p.getMemberId(), p.getName(), p.getCreatedAt(), p.getDelFlag());
        }
    }

    @Test
    void updateMyPlant() {
        Long myplantId = 1L;

        int updated = myPlantRepository.updateMyPlant(
                myplantId,
                1L,
                "몬스테라_수정",
                "관엽식물_수정",
                LocalDateTime.of(2025, 12, 1, 0, 0),
                LocalDateTime.of(2025, 12, 31, 0, 0),
                9,
                "배양토_수정",
                "19~26℃"
        );

        log.info("myplantId={}, updatedRows={}", myplantId, updated);
    }

    @Test
    void deletePlant() {
        Long myplantId = 2L;

        int deleted = myPlantRepository.deletePlant(myplantId, LocalDateTime.now());

        log.info(" myplantId={}, updatedRows={}", myplantId, deleted);
    }
}
