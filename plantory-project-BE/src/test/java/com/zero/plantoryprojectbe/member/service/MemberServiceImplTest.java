package com.zero.plantoryprojectbe.member.service;

import com.zero.plantoryprojectbe.member.Member;
import com.zero.plantoryprojectbe.member.MemberRepository;
import com.zero.plantoryprojectbe.profile.dto.MemberSignUpRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private MemberServiceImpl memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        memberService = new MemberServiceImpl(memberRepository, bCryptPasswordEncoder);
    }

    @Test
    @DisplayName("아이디 중복이 존재하면 true 반환")
    void testIsDuplicateMembernameTrue() {
        when(memberRepository.existsByMembernameAndDelFlagIsNull("testUser"))
                .thenReturn(true);

        boolean result = memberService.isDuplicateMembername("testUser");

        assertTrue(result);
    }

    @Test
    @DisplayName("아이디 중복이 없으면 false 반환")
    void testIsDuplicateMembernameFalse() {
        when(memberRepository.existsByMembernameAndDelFlagIsNull("testUser"))
                .thenReturn(false);

        boolean result = memberService.isDuplicateMembername("testUser");

        assertFalse(result);
    }

    @Test
    @DisplayName("닉네임 중복이 존재하면 true 반환")
    void testIsDuplicateNicknameTrue() {
        when(memberRepository.existsByNicknameAndDelFlagIsNull("nick"))
                .thenReturn(true);

        boolean result = memberService.isDuplicateNickname("nick");

        assertTrue(result);
    }

    @Test
    @DisplayName("닉네임 중복이 없으면 false 반환")
    void testIsDuplicateNicknameFalse() {
        when(memberRepository.existsByNicknameAndDelFlagIsNull("nick"))
                .thenReturn(false);

        boolean result = memberService.isDuplicateNickname("nick");

        assertFalse(result);
    }

    @Test
    @DisplayName("회원가입 실패 - 아이디 중복 오류")
    void testSignUpDuplicateMembername() {
        MemberSignUpRequest request = MemberSignUpRequest.builder()
                .membername("aaa")
                .nickname("aaa")
                .password("12345678")
                .phone("01012345678")
                .address("서울")
                .build();

        when(memberRepository.existsByMembernameAndDelFlagIsNull("aaa"))
                .thenReturn(true);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> memberService.signUp(request)
        );

        assertEquals("이미 사용 중인 아이디입니다.", exception.getMessage());

        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    @DisplayName("회원가입 실패 - 닉네임 중복 오류")
    void testSignUpDuplicateNickname() {
        MemberSignUpRequest request = MemberSignUpRequest.builder()
                .membername("aaa")
                .nickname("aaa")
                .password("12345678")
                .phone("01012345678")
                .address("서울")
                .build();

        when(memberRepository.existsByMembernameAndDelFlagIsNull("aaa"))
                .thenReturn(false);
        when(memberRepository.existsByNicknameAndDelFlagIsNull("aaa"))
                .thenReturn(true);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> memberService.signUp(request)
        );

        assertEquals("이미 사용 중인 닉네임입니다.", exception.getMessage());

        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    @DisplayName("회원가입 성공 - save 호출되고 password는 인코딩된 값으로 저장")
    void testSignUpSuccess() {
        MemberSignUpRequest request = MemberSignUpRequest.builder()
                .membername("newUser")
                .nickname("뉴유저")
                .password("12345678")
                .phone("01012345678")
                .address("서울")
                .build();

        when(memberRepository.existsByMembernameAndDelFlagIsNull("newUser"))
                .thenReturn(false);
        when(memberRepository.existsByNicknameAndDelFlagIsNull("뉴유저"))
                .thenReturn(false);

        when(memberRepository.save(any(Member.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        memberService.signUp(request);

        verify(memberRepository, times(1)).save(argThat(saved ->
                saved != null
                        && saved.getMembername().equals("newUser")
                        && !saved.getPassword().equals("12345678")
                        && bCryptPasswordEncoder.matches("12345678", saved.getPassword())
        ));
    }
}
