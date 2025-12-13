package com.zero.plantoryprojectbe.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zero.plantoryprojectbe.auth.PrincipalDetails;
import com.zero.plantoryprojectbe.repository.User;
import com.zero.plantoryprojectbe.repository.UserRepository;
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
    public JwtBasicAuthenticationFilter(AuthenticationManager authenticationManager,  UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
        log.info("인증 인가 사용자 정의 필터");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        super.doFilterInternal(request, response, chain);
        log.info("doFilter"+request.getRequestURI());

        String jwtToken = request.getHeader(JwtProperties.HEADER_STRING);
        log.info("jwtToken:"+jwtToken);
        if(jwtToken == null||!jwtToken.trim().startsWith(JwtProperties.TOKEN_PREFIX)){
            chain.doFilter(request,response);
            return;
        }
        jwtToken = jwtToken.replace(JwtProperties.TOKEN_PREFIX,"");

        String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
                .build()
                .verify(jwtToken)
                .getClaim("username").asString();
        log.info("username:"+username);

        if(username!=null){
            User user = userRepository.findByUsername(username);
            PrincipalDetails details = new PrincipalDetails(user);

            Authentication auth =
                    new UsernamePasswordAuthenticationToken(details,null,details.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        chain.doFilter(request,response);
    }
}
