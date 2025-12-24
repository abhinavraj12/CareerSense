package com.careersense.app.controller;

import com.careersense.app.model.User;
import com.careersense.app.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepo;

    @GetMapping("/user/me")
    public User me(Authentication auth) {
        return userRepo.findByEmail(auth.getName()).orElseThrow();
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse res) {
        Cookie c = new Cookie("JWT", null);
        c.setPath("/");
        c.setHttpOnly(true);
        c.setMaxAge(0);
        res.addCookie(c);
    }
}
