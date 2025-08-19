package com.forumhub.api;

import com.forumhub.api.dto.*;
import com.forumhub.security.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final TokenService tokenService;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public AuthController(AuthenticationManager am, TokenService ts, UserRepository ur, PasswordEncoder pe) {
        this.authManager = am; this.tokenService = ts; this.userRepo = ur; this.encoder = pe;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest req) {
        var authToken = new UsernamePasswordAuthenticationToken(req.username(), req.password());
        authManager.authenticate(authToken);
        var token = tokenService.generate(req.username());
        return ResponseEntity.ok(new TokenResponse(token, "Bearer"));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequest req) {
        if (userRepo.findByUsername(req.username()).isPresent()) return ResponseEntity.badRequest().body("Usuário já existe");
        var user = AppUser.builder().username(req.username()).password(encoder.encode(req.password())).enabled(true).build();
        userRepo.save(user);
        return ResponseEntity.ok().build();
    }
}

