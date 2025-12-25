package com.zero.plantoryprojectbe.global.security;

import com.zero.plantoryprojectbe.member.MemberMapper;
import com.zero.plantoryprojectbe.profile.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberDetailService implements UserDetailsService {

    private final MemberMapper memberMapper;

    public MemberDetail loadUserById(Long memberId) {
        MemberResponse memberResponse =
                memberMapper.selectByMemberId(memberId);

        if (memberResponse == null) {
            throw new UsernameNotFoundException(
                    "존재하지 않는 사용자 ID: " + memberId
            );
        }

        return new MemberDetail(memberResponse);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberResponse memberResponse = memberMapper.selectByMembername(username);

        if(memberResponse == null) {
            throw new UsernameNotFoundException("존재하지 않는 사용자입니다: " + username);
        }

        return new MemberDetail(memberResponse);
    }

    public void resetStopDay(Long memberId) {
        memberMapper.resetStopDay(memberId);
    }

}
