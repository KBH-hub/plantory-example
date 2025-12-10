package com.oopsw.ejwt.auth;

import com.oopsw.ejwt.jwt.JwtAuthenticationFilter;
import com.oopsw.ejwt.jwt.JwtBasicAuthenticationFilter;
import com.oopsw.ejwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CorsFilter corsFilter;

    //지난번과 다른 이유 : 수동으로 작업, 인자값 필요
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration ac) throws Exception {
        return ac.getAuthenticationManager();
    }

    // 매번 메모리에 올릴 필요 X -> DI 필요한 상황 (실무에선 바뀔 수 있음)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager am, UserRepository userRepository) throws Exception {
        http.csrf(csrf -> csrf.disable());
        // 세션을 사용하지 않음
        http.sessionManagement(session -> {
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.formLogin(form -> form.disable());
            http.logout(logout -> logout.disable());
            http.httpBasic(httpBasic -> httpBasic.disable());
            // 사용자가 직접 필터를 적용
            System.out.println("Security Config");
            http.addFilter(corsFilter);
            http.addFilter(new JwtAuthenticationFilter(am)); // 기존에 적용된거 놔두고 내가 만든게 적용됨
            http.addFilter(new JwtBasicAuthenticationFilter(am, userRepository));


            http.authorizeHttpRequests(ar ->
                    ar.requestMatchers("/api/jwt/user/**").authenticated()
                            .requestMatchers("/api/jwt/manager/**").hasAnyRole("ADMIN", "MANAGER") // admin, manager만 들어오게 함
                            .requestMatchers("/api/jwt/admin/**").hasAnyRole("ADMIN") // admin만 들어오게 함
                            .anyRequest().permitAll());
        });
        return http.build();
    }


}
