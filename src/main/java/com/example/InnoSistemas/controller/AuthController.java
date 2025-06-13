package com.example.InnoSistemas.controller;

import com.example.InnoSistemas.login.AuthRequest;
import com.example.InnoSistemas.security.JwtUtil;
import com.example.InnoSistemas.service.EstudianteDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EstudianteDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        // 1. Autenticar usuario (verifica email/password)
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        // 2. Generar token
        String jwt = jwtUtil.generateToken(request.getEmail());

        // 3. Devolver respuesta
        return ResponseEntity.ok(Map.of(
                "jwt", jwt,
                "message", "Login exitoso",
                "success", true
        ));
    }

    public record LoginRequest(String email, String password) {}
}
