package com.zero.plantoryprojectbe.member.mapper;

import com.zero.plantoryprojectbe.profile.ProfileMapper;
import com.zero.plantoryprojectbe.profile.dto.ProfileUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class ProfileMapperTest {

    @Autowired
    ProfileMapper profileMapper;

    @Test
    @DisplayName("내 프로필 조회")
    void selectProfileInfoTest() {
        var result = profileMapper.selectProfileInfo(1L);
        assertNotNull(result);
        log.info(result.toString());
    }


    @Test
    @DisplayName("내 정보 수정")
    void updateProfileInfoTest() {
        ProfileUpdateRequest request = ProfileUpdateRequest.builder()
                .memberId(1L)
                .nickname("홍길동22")
                .phone("010-1111-2222")
                .address("부산광역시")
                .build();

        int updated = profileMapper.updateProfileInfo(request);
        assertTrue(updated > 0);
        log.info("내 정보 수정 로그결과: "+updated);
    }

    @Test
    @DisplayName("회원 탈퇴")
    void deleteMemberByIdTest() {
        int result = profileMapper.deleteMemberById(1L);
        assertTrue(result > 0);
        log.info(String.valueOf(result));
    }
}
