package com.oopsw.ejwt.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.oopsw.ejwt.auth.PrincipalDetails;
import com.oopsw.ejwt.repository.User;
import com.oopsw.ejwt.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

@Log4j2
public class JwtBasicAuthenticationFilter extends BasicAuthenticationFilter {
    private UserRepository userRepository;
    public JwtBasicAuthenticationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager); // 필수 (생성자 필수)
        this.userRepository = userRepository;
//        System.out.println("인증 - 인가 사용자 정의 필터");
        log.info("인증 - 인가 사용자 정의 필터");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 매개인자에 FilterChain chain -> 순서에 상관 없이 필요하면 가져다 쓰는 상황
//        super.doFilterInternal(request, response, chain);
        log.info("dofilter"+request.getRequestURI()); // URI - 식별자 / URN - 네임 / URL - 아이피로 구분
        //(1) header에 원하는 토큰이 있는지 확인
        String jwtToken = request.getHeader(JwtProperties.HEADER_STRING);
        log.info("jwtToken:"+jwtToken);
        if (jwtToken == null || !jwtToken.trim().startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        // null이 아닐때 앞부분(Bearar) 뺀 나머지 부분을 가져가야 함
        jwtToken = jwtToken.replace(JwtProperties.TOKEN_PREFIX, "");
        //(2) JWT 토큰에서 내가 원하는 값을 추출
        String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
                .build()
                .verify(jwtToken).getClaim("username").asString();
        log.info("username:"+username);
        // 유효한 시간 여부 체크
        //(3) 유효한 정보인지 확인 - 인증, 인가 상태를 체크해서 전달
        if(username!=null){ // 확실한 계정이라면
            User user = userRepository.findByUsername(username); // 수동으로 찾는 상황 - 인증은 되는데 인가가 안될 수 있음
            log.info("repo user = {}", user);
            PrincipalDetails details = new PrincipalDetails(user);
            Authentication auth =
                    new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
            //
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        chain.doFilter(request, response);
    }
}
