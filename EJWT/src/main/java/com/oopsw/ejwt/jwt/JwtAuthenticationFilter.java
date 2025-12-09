package com.oopsw.ejwt.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.oopsw.ejwt.auth.PrincipalDetails;
import com.oopsw.ejwt.vo.UserVO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

//수동으로 토큰 만들어야하는 상황
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    // Yes도 있고 No도 있는 상황, request로 들어온 date - JSON으로 -> Form 태그 사용하면 안됨
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("login 시도 필터");
        ObjectMapper mapper = new ObjectMapper(); // json 자동으로 매핑
        try {// 상속 받은거라 맘대로 throw 못함 -> try catch 사용
            //(1) 로그인 할 준비 완료
            UserVO inputData = mapper.readValue(request.getInputStream(), UserVO.class); // vo나 entity로 넘겨야 함
//            System.out.println("inputData:"+inputData); // 확인용
            //로그인 시도 formLogin 사용 불가 -> 계정 token 으로 변경하는 과정 필요
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(inputData.getUsername(), inputData.getPassword());
            //(2) 실제 로그인
            Authentication auth = authenticationManager.authenticate(authRequest); // details, detailsService 자동으로 가져와주는 것
            PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal(); // object 타입으로 가져오고 있으므로 캐스팅 필요
            //System.out.println(principalDetails.getUser());
            //System.out.println(principalDetails.getUser().getEmail());
            return auth;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
//        return super.attemptAuthentication(request, response); // 이거하면 안됨 무조건 돌아감
    }

    //자동으로 Yes만 넘겨주는 상황
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        super.successfulAuthentication(request, response, chain, authResult);
        //(3) 성공적으로 로그인 하는 상황 - JWT 생성
        System.out.println("login ok~~~");
        PrincipalDetails resultDetails = (PrincipalDetails) authResult.getPrincipal();
        // 이하 token에 들어가는 형식 - 회사에서 정하면 됨
        String jwtToken = JWT.create()
                .withSubject(resultDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", resultDetails.getUser().getId())
                .withClaim("username", resultDetails.getUser().getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
//                .sign(Algorithm.HMAC512(JwtProperties.SECRET.getBytes())); // 문자열은 byte 배열로 되어있음
        System.out.println(jwtToken);
        //(4) 웹 브라우저에 전달
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
        response.getWriter().println(Map.of("message", "loginOK")); // Writer -> 헤더 자동으로 넘어감, 따로 붙일 필요 X
    }
}
