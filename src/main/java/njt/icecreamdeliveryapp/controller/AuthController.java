/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.icecreamdeliveryapp.controller;

/**
 *
 * @author milam
 */
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import njt.icecreamdeliveryapp.dto.impl.AuthResponse;
import njt.icecreamdeliveryapp.dto.impl.ForgotPasswordRequest;
import njt.icecreamdeliveryapp.dto.impl.LoginRequest;
import njt.icecreamdeliveryapp.dto.impl.RegisterRequest;
import njt.icecreamdeliveryapp.dto.impl.ResetPasswordRequest;
import njt.icecreamdeliveryapp.dto.impl.UserDto;
import njt.icecreamdeliveryapp.entity.impl.User;
import njt.icecreamdeliveryapp.entity.impl.VerificationToken;
import njt.icecreamdeliveryapp.mapper.impl.UserMapper;
import njt.icecreamdeliveryapp.repository.impl.UserRepository;
import njt.icecreamdeliveryapp.repository.impl.VerificationTokenRepository;
import njt.icecreamdeliveryapp.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth")
public class AuthController {

    private final AuthService authService;
    private final VerificationTokenRepository tokens;
    private final UserRepository users;
    private final UserMapper userMapper;

    public AuthController(AuthService authService, VerificationTokenRepository tokens, UserRepository users, UserMapper userMapper) {
        this.authService = authService;
        this.tokens = tokens;
        this.users = users;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterRequest req) throws Exception {
        return ResponseEntity.ok(authService.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(Authentication auth) throws Exception {
        // auth.getName() je username iz SecurityContext-a
        User u = users.findByUsername(auth.getName());
        return ResponseEntity.ok(userMapper.toDto(u));
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam String token) {
        VerificationToken vt = tokens.find(token);
        if (vt == null) {
            return ResponseEntity.status(org.springframework.http.HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, "http://localhost:3000/verify?status=error")
                    .build();
        }
        if (vt.isExpired()) {
            tokens.delete(vt);
            return ResponseEntity.status(org.springframework.http.HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, "http://localhost:3000/verify?status=expired")
                    .build();
        }

        User u = vt.getUser();
        u.setEnabled(true);
        users.save(u);
        tokens.delete(vt); 

        return ResponseEntity.status(org.springframework.http.HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "http://localhost:3000/verify?status=success")
                .build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        authService.requestPasswordReset(request.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request.getToken(), request.getPassword());
        return ResponseEntity.ok("Password has been successfully reset.");
    }

}
