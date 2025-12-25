package com.zero.plantoryprojectbe.member.mapper;

import com.zero.plantoryprojectbe.profile.ProfileSharingHistoryMapper;
import com.zero.plantoryprojectbe.profile.dto.ProfileSharingHistoryListRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j
@Transactional
class ProfileHistoryMapperTest {

    @Autowired
    ProfileSharingHistoryMapper profileSharingHistoryMapper;

    @Test
    @DisplayName("관심 나눔 카운트 조회")
    void countByInterestCountTest() {
        Long memberId = 1L;

        int count = profileSharingHistoryMapper.countByInterestCount(memberId);

        assertTrue(count >= 0, "관심 나눔 수 조회 실패");
        log.info("관심 나눔 수 = {}", count);
    }

    @Test
    @DisplayName("완료된 나눔 카운트 조회")
    void countByCompletedSharingCountTest() {
        Long memberId = 1L;

        int count = profileSharingHistoryMapper.countByCompletedSharingCount(memberId);

        assertTrue(count >= 0, "완료된 나눔 수 조회 실패");
        log.info("완료된 나눔 수 = {}", count);
    }

    @Test
    @DisplayName("프로필 - 내가 올린 나눔 내역 조회")
    void selectMySharingListTest() {
        ProfileSharingHistoryListRequest request = new ProfileSharingHistoryListRequest();
        request.setMemberId(1L);
        request.setLimit(10);
        request.setOffset(0);

        var result = profileSharingHistoryMapper.selectMySharingList(request);

        assertNotNull(result);
    }

    @Test
    @DisplayName("프로필 - 내가 받은 나눔 내역 조회")
    void selectReceivedSharingListTest() {
        ProfileSharingHistoryListRequest request = new ProfileSharingHistoryListRequest();
        request.setMemberId(1L);
        request.setLimit(10);
        request.setOffset(0);

        var result = profileSharingHistoryMapper.selectReceivedSharingList(request);

        assertNotNull(result);
    }


}
