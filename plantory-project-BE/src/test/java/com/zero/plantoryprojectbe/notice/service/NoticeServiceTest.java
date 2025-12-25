package com.zero.plantoryprojectbe.notice.service;

import com.zero.plantoryprojectbe.notice.dto.NoticeDTO;
import com.zero.plantoryprojectbe.global.plantoryEnum.NoticeTargetType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
class NoticeServiceTest {

    @Autowired
    private NoticeService noticeService;

    @Test
    @DisplayName("수신자 기준 알림 목록 조회")
    void getNoticeByReceiverTest() {
        Long receiverId = 1L;

        List<NoticeDTO> result = noticeService.getNoticeByReceiver(receiverId);

        log.info("result={}", result);
    }

    @Test
    @DisplayName("알림 등록")
    void registerNoticeTest() {
        NoticeDTO noticeDTO = NoticeDTO.builder()
                .receiverId(2L)
                .targetType(NoticeTargetType.SHARING)
                .targetId(5L)
                .content("test알림입니다.")
                .build();

        int result = noticeService.registerNotice(noticeDTO);

        log.info(String.valueOf(result));
    }

    @Test
    @DisplayName("알림 읽음 처리")
    void updateNoticeReadFlag() {
        Long noticeId = 1L;

        int result = noticeService.updateNoticeReadFlag(noticeId);

        log.info(String.valueOf(result));
    }

    @Test
    @DisplayName("수신자의 모든 알림 삭제")
    void removeAllNoticeTest() {
        Long receiverId = 1L;

        int result = noticeService.removeAllNotice(receiverId);

        log.info(String.valueOf(result));
    }
}
