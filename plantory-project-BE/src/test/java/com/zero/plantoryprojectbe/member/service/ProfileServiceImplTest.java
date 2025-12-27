package com.zero.plantoryprojectbe.member.service;

import com.zero.plantoryprojectbe.member.Member;
import com.zero.plantoryprojectbe.member.MemberRepository;
import com.zero.plantoryprojectbe.member.Role;
import com.zero.plantoryprojectbe.profile.ProfileMapper;
import com.zero.plantoryprojectbe.profile.dto.ProfileInfoResponse;
import com.zero.plantoryprojectbe.profile.dto.ProfileUpdateRequest;
import com.zero.plantoryprojectbe.profile.service.ProfileServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceImplTest {

    @Mock
    private ProfileMapper profileMapper;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private ProfileServiceImpl profileService;

    @Test
    @DisplayName("내 정보 조회 테스트")
    void getProfileInfoTest() {
        // given
        Long memberId = 1L;

        Member member = Member.create("testuser", "encodedPw");

        ReflectionTestUtils.setField(member, "memberId", memberId);
        ReflectionTestUtils.setField(member, "nickname", "테스트닉");
        ReflectionTestUtils.setField(member, "phone", "010-1111-2222");
        ReflectionTestUtils.setField(member, "address", "서울특별시 금천구");
        ReflectionTestUtils.setField(member, "role", Role.USER);
        ReflectionTestUtils.setField(member, "noticeEnabled", true);
        ReflectionTestUtils.setField(member, "sharingRate", null);
        ReflectionTestUtils.setField(member, "delFlag", null);

        when(memberRepository.findByMemberIdAndDelFlagIsNull(memberId))
                .thenReturn(Optional.of(member));

        //when
        ProfileInfoResponse result = profileService.getProfileInfo(memberId);

        //then
        assertNotNull(result);
        assertEquals("테스트닉", result.getNickname());
        assertEquals(BigDecimal.ZERO, result.getSharingRate()); // from()에서 null -> 0 처리했으면
        verify(memberRepository, times(1)).findByMemberIdAndDelFlagIsNull(memberId);
    }


    @Test
    @DisplayName("알림 설정 변경 테스트")
    void updateNoticeEnabledTest() {
        when(profileMapper.updateNoticeEnabled(1L, 1)).thenReturn(1);

        boolean result = profileService.updateNoticeEnabled(1L, 1);

        assertTrue(result);
        verify(profileMapper, times(1)).updateNoticeEnabled(1L, 1);
    }

    @Test
    @DisplayName("회원 정보 수정 테스트")
    void updateProfileInfoTest() {
        ProfileUpdateRequest request = ProfileUpdateRequest.builder()
                .memberId(1L)
                .nickname("새닉네임")
                .phone("010-1234-5678")
                .build();

        when(profileMapper.updateProfileInfo(request)).thenReturn(1);

        boolean result = profileService.updateProfileInfo(request);

        assertTrue(result);
        verify(profileMapper, times(1)).updateProfileInfo(request);
    }

    @Test
    @DisplayName("회원 탈퇴 테스트")
    void deleteMemberByIdTest() {
        when(profileMapper.deleteMemberById(1L)).thenReturn(1);

        boolean result = profileService.deleteMemberById(1L);

        assertTrue(result);
        verify(profileMapper, times(1)).deleteMemberById(1L);
    }
}
