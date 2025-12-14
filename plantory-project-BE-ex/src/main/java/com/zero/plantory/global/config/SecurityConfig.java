package com.zero.plantory.global.config;

import com.zero.plantory.global.jwt.JwtAuthenticationFilter;
import com.zero.plantory.global.jwt.JwtBasicAuthenticationFilter;
import com.zero.plantory.global.repository.UserRepository;
import com.zero.plantory.global.security.MemberAuthFailureHandler;
import com.zero.plantory.global.security.MemberLoginSuccessHandler;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final StopUserFilter stopUserFilter;

    private final MemberLoginSuccessHandler memberLoginSuccessHandler;
    private final UserDetailsService userDetailsService;
    private final Environment env;
    private final MemberAuthFailureHandler memberAuthFailureHandler;

    private final CorsFilter corsFilter;

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/static/**", "/css/**", "/js/**", "/image/**","/data/**");
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager am, UserRepository userRepository) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/login",
                                "/signUp",
                                "/members/login",
                                "/members/signUp",
                                "/termsOfService",
                                "/api/members/exists",
                                "/api/**"
                        ).permitAll()

                        .requestMatchers(
                                "/community/**",
                                "/sharing/**",
                                "/dashboard",
                                "/my/**",
                                "/profile"
                        ).authenticated()

                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login-process")
                        .usernameParameter("membername")
                        .successHandler(memberLoginSuccessHandler)
                        .failureUrl("/login?error=true")
                        .failureHandler(memberAuthFailureHandler)
                        .permitAll()
                )
                .rememberMe(remember -> remember
                        .key(env.getProperty("security.remember-me.key"))
                        .rememberMeParameter("remember-me")
                        .tokenValiditySeconds(60 * 60 * 24 * 7)
                        .userDetailsService(userDetailsService)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?message=withdraw")
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write("{\"error\":\"권한이 없습니다.\"}");
                        }))
                );
                http
                .sessionManagement(session -> {
                            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                            http.formLogin(form -> form.disable());
                            http.logout(logout -> logout.disable());
                            http.httpBasic(httpBatic -> httpBatic.disable());
                            System.out.println("Security Config");
                            http.addFilter(corsFilter);
                            http.addFilter(new JwtAuthenticationFilter(am));
                            http.addFilter(new JwtBasicAuthenticationFilter(am, userRepository));
                        }
                );
                http
                .addFilterBefore(stopUserFilter, LogoutFilter.class);

        return http.build();
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
