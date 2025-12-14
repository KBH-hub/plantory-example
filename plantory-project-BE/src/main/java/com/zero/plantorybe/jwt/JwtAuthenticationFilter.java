package com.zero.plantorybe.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zero.plantorybe.auth.PrincipalDetails;
import com.zero.plantorybe.vo.UserVO;
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

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("login 시도 필터");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            UserVO inputData = objectMapper.readValue(request.getInputStream(), UserVO.class);
//            System.out.println("inputData: " + inputData);
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(inputData.getUsername(), inputData.getPassword());
            Authentication auth =  authenticationManager.authenticate(authRequest);
            PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal();
//            System.out.println(principalDetails.getUser());
            return auth;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("로그인 성공");
        PrincipalDetails resultDetails = (PrincipalDetails) authResult.getPrincipal();
        String JwtToken = JWT.create()
                .withSubject(resultDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", resultDetails.getUser().getId())
                .withClaim("username", resultDetails.getUser().getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
//        System.out.println(JWT.decode(JwtToken).getHeader());
//        System.out.println(JWT.decode(JwtToken).getPayload());
//        System.out.println(JWT.decode(JwtToken).getSignature());
        System.out.println("JWT Token: " + JwtToken);

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + JwtToken);
        response.getWriter().println(Map.of("message","login success"));
//        super.successfulAuthentication(request, response, chain, authResult);
    }
}
