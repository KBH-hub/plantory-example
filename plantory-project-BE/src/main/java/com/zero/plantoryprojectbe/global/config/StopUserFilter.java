package com.zero.plantoryprojectbe.global.config;

import com.zero.plantoryprojectbe.global.security.MemberPrincipal;
import com.zero.plantoryprojectbe.member.Member;
import com.zero.plantoryprojectbe.member.MemberRepository;
import com.zero.plantoryprojectbe.profile.ProfileMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StopUserFilter extends OncePerRequestFilter {

    private final ProfileMapper profileMapper;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated()
                && auth.getPrincipal() instanceof MemberPrincipal userDetail) {

            Long memberId = userDetail.getMemberId();
            Optional<Member> member = memberRepository.findByMemberIdAndDelFlagIsNull(memberId);

            if (member.isEmpty()) {
                request.getSession().invalidate();
                SecurityContextHolder.clearContext();
                String error = URLEncoder.encode("계정 정보를 찾을 수 없습니다.", StandardCharsets.UTF_8);
                response.sendRedirect("/login?error=" + error);
                return;
            }



            LocalDateTime stopDay = member.get().getStopDay();
            LocalDateTime today = LocalDateTime.now();

            if (stopDay != null && stopDay.isAfter(today)) {
                request.getSession().invalidate();
                SecurityContextHolder.clearContext();
                String stopped = URLEncoder.encode("정지되었습니다.", StandardCharsets.UTF_8);
                response.sendRedirect("/login?error=" + stopped);
                return;
            }

        }

        filterChain.doFilter(request, response);
    }
}

