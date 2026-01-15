package com.tarea.facturacion_api.controller;

import com.tarea.facturacion_api.dto.AuthRequest;
import com.tarea.facturacion_api.dto.AuthResponse;
import com.tarea.facturacion_api.model.Usuario;
import com.tarea.facturacion_api.repository.UsuarioRepository;
import com.tarea.facturacion_api.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario usuario) {
        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("error", "El nombre de usuario ya existe."));
        }
        
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
        
        // CORRECCIÓN: Devolvemos un JSON, no un texto plano
        return ResponseEntity.ok(Collections.singletonMap("mensaje", "Usuario registrado exitosamente"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            
            final String jwt = jwtUtil.generateToken(request.getUsername());
            Usuario usuario = usuarioRepository.findByUsername(request.getUsername()).orElseThrow();

            return ResponseEntity.ok(new AuthResponse(jwt, usuario.getUsername(), usuario.getRole()));
            
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Collections.singletonMap("error", "Credenciales incorrectas"));
        }
    }
}