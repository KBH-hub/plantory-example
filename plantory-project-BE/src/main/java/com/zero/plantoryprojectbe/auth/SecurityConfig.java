package com.zero.plantoryprojectbe.auth;

import com.zero.plantoryprojectbe.jwt.JwtAuthenticationFilter;
import com.zero.plantoryprojectbe.jwt.JwtBasicAuthenticationFilter;
import com.zero.plantoryprojectbe.repository.UserRepository;
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

        http.sessionManagement(session -> {
           session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
           http.formLogin(form -> form.disable());
           http.logout(logout -> logout.disable());
           http.httpBasic(httpBatic -> httpBatic.disable());
            System.out.println("Security Config");
           http.addFilter(corsFilter);
           http.addFilter(new JwtAuthenticationFilter(am));
           http.addFilter(new JwtBasicAuthenticationFilter(am, userRepository));
        });

            http.authorizeHttpRequests(ar ->
                    ar.requestMatchers("/api/jwt/user/**").authenticated()
                            .requestMatchers("/api/jwt/manager/**").hasAnyRole("ADMIN", "MANAGER") // admin, manager만 들어오게 함
                            .requestMatchers("/api/jwt/admin/**").hasAnyRole("ADMIN") // admin만 들어오게 함
                            .anyRequest().permitAll());
        return http.build();
    }


}
