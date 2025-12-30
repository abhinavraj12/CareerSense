package com.careersense.app.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2SuccessHandler successHandler;
    private final JwtFilter jwtFilter;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // Disable CSRF (JWT + HttpOnly cookies)
                .csrf(csrf -> csrf.disable())

                //  Stateless backend (JWT-based)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // AUTHORIZATION RULES
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/index.html",
                                "/home.html",
                                "/resume-score.html",
                                "/style.css",
                                "/js/**",
                                "/img/**"
                        ).permitAll()

                        .requestMatchers("/oauth2/**").permitAll()
                        .requestMatchers("/job-match.html").authenticated()
                        .requestMatchers("/api/resume-score/**").authenticated()
                        .requestMatchers("/api/**").authenticated()

                        .anyRequest().authenticated()
                )

                // REDIRECT UNAUTHENTICATED USERS TO LOGIN
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            // API requests → 401
                            if (request.getRequestURI().startsWith("/api")) {
                                response.setStatus(401);
                            } else {
                                // UI requests → redirect
                                response.sendRedirect("/index.html");
                            }
                        })
                )

                // GOOGLE OAUTH2 LOGIN
                .oauth2Login(oauth -> oauth
                        .authorizationEndpoint(auth -> auth
                                .authorizationRequestResolver(
                                        new CustomOAuth2AuthorizationRequestResolver(
                                                clientRegistrationRepository
                                        )
                                )
                        )
                        .successHandler(successHandler)
                )

                // JWT FILTER (VALIDATES COOKIE TOKEN)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}