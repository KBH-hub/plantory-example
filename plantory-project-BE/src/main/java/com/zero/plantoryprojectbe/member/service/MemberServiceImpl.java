package com.zero.plantoryprojectbe.member.service;

import com.zero.plantoryprojectbe.member.Member;
import com.zero.plantoryprojectbe.member.MemberRepository;
import com.zero.plantoryprojectbe.member.Role;
import com.zero.plantoryprojectbe.profile.dto.MemberSignUpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional(readOnly = true)
    public boolean isDuplicateMembername(String membername) {
        return memberRepository.existsByMembernameAndDelFlagIsNull(membername);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isDuplicateNickname(String nickname) {
        return memberRepository.existsByNicknameAndDelFlagIsNull(nickname);
    }

    @Override
    @Transactional
    public void signUp(MemberSignUpRequest request) {

        if (memberRepository.existsByMembernameAndDelFlagIsNull(request.getMembername())) {
            throw new IllegalStateException("이미 사용 중인 아이디입니다.");
        }
        if (memberRepository.existsByNicknameAndDelFlagIsNull(request.getNickname())) {
            throw new IllegalStateException("이미 사용 중인 닉네임입니다.");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());

        Member member = Member.createForSignUp(
                request.getMembername(),
                encodedPassword,
                request.getNickname(),
                request.getPhone(),
                request.getAddress()
        );

        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public Member findById(Long memberId) {
        return memberRepository.findByMemberIdAndDelFlagIsNull(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원"));
    }
}
