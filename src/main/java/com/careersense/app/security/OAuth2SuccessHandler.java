package com.careersense.app.security;

import com.careersense.app.model.User;
import com.careersense.app.repository.UserRepository;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User user = (OAuth2User) authentication.getPrincipal();

        String email = user.getAttribute("email");
        String name = user.getAttribute("name");
        String picture = user.getAttribute("picture");
        String googleId = user.getAttribute("sub");

        if (email == null) {
            response.sendError(500, "Google email not found");
            return;
        }

        userRepo.findByEmail(email).orElseGet(() ->
                userRepo.save(User.builder()
                        .email(email)
                        .name(name)
                        .picture(picture)
                        .googleId(googleId)
                        .build())
        );

        String jwt = jwtUtil.generateToken(email);

        Cookie cookie = new Cookie("JWT", jwt);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // localhost
        cookie.setPath("/");
        cookie.setMaxAge(86400);

        response.addCookie(cookie);
        response.sendRedirect("/home.html");
    }
}