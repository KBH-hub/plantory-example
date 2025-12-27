package com.zero.plantoryprojectbe.profile.service;

import com.zero.plantoryprojectbe.member.Member;
import com.zero.plantoryprojectbe.member.MemberRepository;
import com.zero.plantoryprojectbe.profile.dto.ProfileInfoResponse;
import com.zero.plantoryprojectbe.profile.dto.ProfileUpdateRequest;
import com.zero.plantoryprojectbe.profile.dto.PublicProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public ProfileInfoResponse getProfileInfo(Long memberId) {
        Member member = memberRepository.findByMemberIdAndDelFlagIsNull(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원"));
        return ProfileInfoResponse.from(member);
    }

    @Override
    @Transactional
    public boolean updateNoticeEnabled(Long memberId, int enabled) {
        Member member = memberRepository.findByMemberIdAndDelFlagIsNull(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원"));

        member.updateNoticeEnabled(enabled == 1);
        return true;
    }

    @Override
    @Transactional
    public boolean updateProfileInfo(ProfileUpdateRequest request) {
        Member member = memberRepository.findByMemberIdAndDelFlagIsNull(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원"));

        member.updateProfileInfo(
                request.getNickname(),
                request.getPhone(),
                request.getAddress()
        );

        return true;
    }

    @Override
    @Transactional
    public boolean deleteMemberById(Long memberId) {
        Member member = memberRepository.findByMemberIdAndDelFlagIsNull(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원"));

        member.softDeleteNow();
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public PublicProfileResponse getPublicProfile(Long memberId) {
        Member member = memberRepository.findByMemberIdAndDelFlagIsNull(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원"));

        return PublicProfileResponse.from(member);
    }

    @Override
    @Transactional
    public boolean changePassword(Long memberId, String oldPassword, String newPassword) {
        Member member = memberRepository.findByMemberIdAndDelFlagIsNull(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원"));

        if (!bCryptPasswordEncoder.matches(oldPassword, member.getPassword())) {
            return false;
        }

        member.changePassword(bCryptPasswordEncoder.encode(newPassword));
        return true;
    }
}