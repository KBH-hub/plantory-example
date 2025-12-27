package com.zero.plantoryprojectbe.global.security.jwt;

import com.zero.plantoryprojectbe.global.security.MemberDetailService;
import com.zero.plantoryprojectbe.global.security.MemberPrincipal;
import com.zero.plantoryprojectbe.member.Member;
import com.zero.plantoryprojectbe.member.MemberRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class TokenProvider {

    private final JwtProperties jwtProperties;
    private final MemberDetailService memberDetailService;
    private final MemberRepository  memberRepository;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String createAccessToken(String memberId) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtProperties.getAccessTokenExpiration());

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setSubject(memberId)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return false;
        } catch (ExpiredJwtException e) {
            log.info("ExpiredJwtException: ", e);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("JwtException: ", e);
            return true;
        }
    }
    
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .requireIssuer(jwtProperties.getIssuer())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);
        Long memberId = Long.valueOf(claims.getSubject());
        MemberPrincipal principal = memberDetailService.loadUserById(memberId);
        Member member = memberRepository.findById(principal.getMemberId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        LocalDateTime stopDay = member.getStopDay();
        if (stopDay != null && stopDay.isAfter(LocalDateTime.now())) {
            throw new RuntimeException("정지된 사용자입니다.");
        }

        return new UsernamePasswordAuthenticationToken(
                principal,
                token,
                principal.getAuthorities()
        );
    }


    public Long getMemberId(String token) {
        Claims claims = parseClaims(token);
        return Long.valueOf(claims.getSubject());
    }

    public String createRefreshToken(String memberId) {
        Date now = new Date();
        Date expiry = new Date(
                now.getTime() + jwtProperties.getRefreshTokenExpiration()
        );

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setSubject(memberId)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

}
