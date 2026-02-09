package br.edu.ifrn.jeferson.blog.controller;

import br.edu.ifrn.jeferson.blog.dto.LoginRequestDTO;
import br.edu.ifrn.jeferson.blog.dto.TokenResponseDTO;
import br.edu.ifrn.jeferson.blog.exception.ResourceNotFoundException;
import br.edu.ifrn.jeferson.blog.models.Users;
import br.edu.ifrn.jeferson.blog.repository.UserRepository;
import br.edu.ifrn.jeferson.blog.config.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepo, PasswordEncoder encoder, JwtService jwtService) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        Users user = userRepo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + dto.getEmail()));

        if (!encoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = jwtService.generateToken(
                user.getEmail(),
                Map.of("role", user.getRole().name(), "userId", user.getId())
        );

        return ResponseEntity.ok(new TokenResponseDTO(token));
    }
}
