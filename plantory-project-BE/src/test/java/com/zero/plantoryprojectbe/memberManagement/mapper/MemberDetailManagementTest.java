package com.zero.plantoryprojectbe.memberManagement.mapper;

import com.zero.plantoryprojectbe.global.plantoryEnum.DeleteTargetType;
import com.zero.plantoryprojectbe.memberManagement.MemberManagementMapper;
import com.zero.plantoryprojectbe.memberManagement.dto.MemberManagementResponse;
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
class MemberDetailManagementTest {
    @Autowired
    MemberManagementMapper memberManagementMapper;

    @Test
    @DisplayName("회원 관리 화면 조회")
    void selectMemberListTest() {
        String keyword = "hong";
        int limit = 10;
        int offset = 0;

        List<MemberManagementResponse> result =  memberManagementMapper.selectMemberList(keyword, limit, offset);

        log.info("result:{}", result);
    }

    @Test
    @DisplayName("부적절 글, 댓글 삭제")
    void deleteContentTest() {
        DeleteTargetType targetType = DeleteTargetType.SHARING;
        Long targetId = 1L;

        int result = memberManagementMapper.deleteContent(targetType, targetId);

        log.info(String.valueOf(result));
    }
}