/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.icecreamdeliveryapp.security;

/**
 *
 * @author milam
 */
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwt;
    private final AppUserDetailsService uds;

    public JwtAuthFilter(JwtService jwt, AppUserDetailsService uds) {
        this.jwt = jwt;
        this.uds = uds;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String auth = req.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);
            String username = null;
            try {
                username = jwt.extractUsername(token);
            } catch (JwtException ex) {
                logger.warn("Invalid JWT token");
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails ud = uds.loadUserByUsername(username);
                if (jwt.isValid(token, ud)) {
                    UsernamePasswordAuthenticationToken at
                            = new UsernamePasswordAuthenticationToken(ud, null, ud.getAuthorities());
                    at.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                    SecurityContextHolder.getContext().setAuthentication(at);
                }
            }
        }
        chain.doFilter(req, res);
    }
}
