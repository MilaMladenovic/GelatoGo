/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.icecreamdeliveryapp.service;

/**
 *
 * @author milam
 */
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import njt.icecreamdeliveryapp.dto.impl.AuthResponse;
import njt.icecreamdeliveryapp.dto.impl.LoginRequest;
import njt.icecreamdeliveryapp.dto.impl.RegisterRequest;
import njt.icecreamdeliveryapp.dto.impl.UserDto;
import njt.icecreamdeliveryapp.entity.impl.PasswordResetToken;
import njt.icecreamdeliveryapp.entity.impl.Role;
import njt.icecreamdeliveryapp.entity.impl.User;
import njt.icecreamdeliveryapp.entity.impl.VerificationToken;
import njt.icecreamdeliveryapp.mapper.impl.UserMapper;
import njt.icecreamdeliveryapp.repository.impl.PasswordResetTokenRepository;
import njt.icecreamdeliveryapp.repository.impl.UserRepository;
import njt.icecreamdeliveryapp.repository.impl.VerificationTokenRepository;
import njt.icecreamdeliveryapp.security.JwtService;
import org.springframework.beans.factory.annotation.Value;

@Service
public class AuthService {

    private final AuthenticationManager authManager;
    private final JwtService jwt;
    private final UserRepository users;
    private final VerificationTokenRepository tokens;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    private final PasswordResetTokenRepository resetTokens;

    private final MailService mail;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    public AuthService(AuthenticationManager authManager, JwtService jwt, UserRepository users, VerificationTokenRepository tokens, PasswordEncoder encoder, UserMapper userMapper, PasswordResetTokenRepository resetTokens, MailService mail) {
        this.authManager = authManager;
        this.jwt = jwt;
        this.users = users;
        this.tokens = tokens;
        this.encoder = encoder;
        this.userMapper = userMapper;
        this.resetTokens = resetTokens;
        this.mail = mail;
    }

    @Transactional
    public UserDto register(RegisterRequest req) throws Exception {
        if (users.existsByUsername(req.getUsername())) {
            throw new Exception("Username already taken");
        }
        if (users.existsByEmail(req.getEmail())) {
            throw new Exception("Email already taken");
        }

        User u = new User();
        u.setUsername(req.getUsername().trim());
        u.setEmail(req.getEmail().trim().toLowerCase());
        u.setPasswordHash(encoder.encode(req.getPassword()));
        u.setFirstName(req.getFirstName().trim());
        u.setLastName(req.getLastName().trim());
        u.setAddress(req.getAddress().trim());
        u.setPhoneNumber(req.getPhoneNumber().trim());
        u.setRole(Role.USER);

        users.save(u);
        var vt = VerificationToken.of(u, 86400);
        tokens.save(vt);

        String verifyUrl = "http://localhost:8080/api/auth/verify?token=" + vt.getToken();
        String html = buildVerificationEmailHtml(
                u.getUsername(),
                verifyUrl
        );

        try {
            mail.sendHtml(u.getEmail(), "Verify your GelatoGo account", html);
        } catch (Exception e) {
            System.err.println("User registration successful, but verification email failed to send to: " + u.getEmail());
        }

        return userMapper.toDto(u);
    }

    public AuthResponse login(LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

        User me = users.findByUsername(req.getUsername());
        String token = jwt.generate(
                (org.springframework.security.core.userdetails.User) auth.getPrincipal(),
                Map.of("role", me.getRole().name()));
        return new AuthResponse(token, userMapper.toDto(me));
    }

    @Transactional
    public void requestPasswordReset(String email) {
        User u = users.findByEmail(email);
        if (u == null) {
            return;
        }

        // napravi token (30min = 1800s)
        PasswordResetToken t = PasswordResetToken.of(u, 1800);
        resetTokens.save(t);

        String link = frontendUrl + "/reset-password?token=" + t.getToken();
        String html = buildResetEmailHtml(u.getUsername(), link);

        mail.sendHtml(u.getEmail(), "Reset your GelatoGo acount password", html);
    }

    public void resetPassword(String token, String password) {
        PasswordResetToken t = resetTokens.find(token);
        if (t == null || t.isUsed() || t.isExpired()) {
            throw new RuntimeException("Invalid or expired token");
        }
        User u = t.getUser();
        u.setPasswordHash(encoder.encode(password));
        users.save(u);

        t.setUsed(true);
        resetTokens.save(t);
    }

    private String buildVerificationEmailHtml(String username, String link) {
        return """
        <div style="margin:0;padding:40px 20px;background:#faf8f5;font-family:Inter,Segoe UI,Arial,sans-serif;color:#1a1a1a;">
        
            <div style="max-width:600px;margin:0 auto;background:#ffffff;border-radius:18px;
                        box-shadow:0 10px 30px rgba(0,0,0,.08);overflow:hidden;">

                <div style="background:#7c3aed;padding:28px;text-align:center;">
                    <h1 style="margin:0;color:#ffffff;font-size:30px;font-weight:700;">
                        🍦 GelatoGo
                    </h1>
                </div>

                <div style="padding:40px;">

                    <h2 style="margin-bottom:18px;font-size:26px;font-weight:700;color:#1a1a1a;">
                        Welcome, %s!
                    </h2>

                    <p style="font-size:16px;line-height:1.7;color:#555;">
                        Thank you for creating your <strong>GelatoGo</strong> account.
                        To start ordering your favorite ice cream for delivery,
                        please verify your email address by clicking the button below.
                    </p>

                    <div style="text-align:center;margin:36px 0;">
                        <a href="%s"
                           style="display:inline-block;
                                  background:#7c3aed;
                                  color:#ffffff;
                                  padding:14px 30px;
                                  border-radius:12px;
                                  font-weight:600;
                                  font-size:16px;
                                  text-decoration:none;">
                            Verify Email
                        </a>
                    </div>

                    <p style="font-size:14px;color:#666;margin-bottom:12px;">
                        If the button doesn't work, copy and paste this link into your browser:
                    </p>

                    <p style="word-break:break-all;
                              background:#f4f1fb;
                              padding:12px;
                              border-radius:8px;
                              font-size:13px;
                              color:#444;">
                        %s
                    </p>

                    <hr style="border:none;border-top:1px solid #ececec;margin:32px 0;">

                    <p style="font-size:13px;color:#777;line-height:1.6;">
                        This verification link is valid for <strong>24 hours</strong>.
                        If you didn't create a GelatoGo account, you can safely ignore this email.
                    </p>

                </div>

            </div>

        </div>
        """.formatted(username, link, link);
    }

    private String buildResetEmailHtml(String username, String link) {
        return """
        <div style="margin:0;padding:40px 20px;background:#faf8f5;font-family:Inter,Segoe UI,Arial,sans-serif;color:#1a1a1a;">
        
            <div style="max-width:600px;margin:0 auto;background:#ffffff;border-radius:18px;
                        box-shadow:0 10px 30px rgba(0,0,0,.08);overflow:hidden;">

                <div style="background:#7c3aed;padding:28px;text-align:center;">
                    <h1 style="margin:0;color:#ffffff;font-size:30px;font-weight:700;">
                        🍦 GelatoGo
                    </h1>
                </div>

                <div style="padding:40px;">

                    <h2 style="margin-bottom:18px;font-size:26px;font-weight:700;color:#1a1a1a;">
                        Hello, %s!
                    </h2>

                    <p style="font-size:16px;line-height:1.7;color:#555;">
                        We received a request to reset the password for your
                        <strong>GelatoGo</strong> account.
                    </p>

                    <p style="font-size:16px;line-height:1.7;color:#555;">
                        Click the button below to choose a new password.
                    </p>

                    <div style="text-align:center;margin:36px 0;">
                        <a href="%s"
                           style="display:inline-block;
                                  background:#7c3aed;
                                  color:#ffffff;
                                  padding:14px 30px;
                                  border-radius:12px;
                                  font-weight:600;
                                  font-size:16px;
                                  text-decoration:none;">
                            Reset Password
                        </a>
                    </div>

                    <p style="font-size:14px;color:#666;margin-bottom:12px;">
                        If the button doesn't work, copy and paste this link into your browser:
                    </p>

                    <p style="word-break:break-all;
                              background:#f4f1fb;
                              padding:12px;
                              border-radius:8px;
                              font-size:13px;
                              color:#444;">
                        %s
                    </p>

                    <hr style="border:none;border-top:1px solid #ececec;margin:32px 0;">

                    <p style="font-size:13px;color:#777;line-height:1.6;">
                        This link is valid for <strong>30 minutes</strong>.
                        If you didn't request a password reset, no further action is required and your password will remain unchanged.
                    </p>

                </div>

            </div>

        </div>
        """.formatted(username, link, link);
    }
}
