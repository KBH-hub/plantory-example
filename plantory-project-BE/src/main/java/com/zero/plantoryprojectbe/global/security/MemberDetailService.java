package com.zero.plantoryprojectbe.global.security;

import com.zero.plantoryprojectbe.member.Member;
import com.zero.plantoryprojectbe.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberPrincipal loadUserById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new UsernameNotFoundException("존재하지 않는 사용자 ID: " + memberId)
                );

        return toPrincipal(member);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByMembernameAndDelFlagIsNull(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("존재하지 않는 사용자입니다: " + username)
                );

        return toPrincipal(member);
    }

    private MemberPrincipal toPrincipal(Member member) {
        return new MemberPrincipal(
                member.getMemberId(),
                member.getMembername(),
                member.getRole().name(),
                member.getPassword()
        );
    }
}
