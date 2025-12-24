package com.careersense.app.security;

import com.careersense.app.repository.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain)
            throws IOException, ServletException {

        if (req.getCookies() != null) {
            for (Cookie cookie : req.getCookies()) {
                if ("JWT".equals(cookie.getName())) {
                    try {
                        String email = jwtUtil.extractEmail(cookie.getValue());

                        if (!userRepo.existsByEmail(email)) {
                            logout(res);
                            res.sendRedirect("/index.html");
                            return;
                        }

                        var auth = new UsernamePasswordAuthenticationToken(email, null, List.of());
                        SecurityContextHolder.getContext().setAuthentication(auth);

                    } catch (Exception e) {
                        logout(res);
                        res.sendRedirect("/index.html");
                        return;
                    }
                }
            }
        }
        chain.doFilter(req, res);
    }

    private void logout(HttpServletResponse res) {
        Cookie c = new Cookie("JWT", null);
        c.setPath("/");
        c.setHttpOnly(true);
        c.setMaxAge(0);
        res.addCookie(c);
    }
}