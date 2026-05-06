package com.heveraldo.controle_financeiro.adapters.in.controller;

import com.heveraldo.controle_financeiro.adapters.in.dto.request.LoginRequestDTO;
import com.heveraldo.controle_financeiro.adapters.in.dto.request.UsuarioRequestDTO;
import com.heveraldo.controle_financeiro.infrastructure.config.TokenService;
import com.heveraldo.controle_financeiro.core.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor // Este cara cria o construtor para as 3 variáveis abaixo
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioService usuarioService; // ESSA LINHA É O QUE RESOLVE SEU ERRO

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequestDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        String token = tokenService.gerarToken(auth.getName());

        return ResponseEntity.ok(token);
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> registrar(@RequestBody @Valid UsuarioRequestDTO data) {
        // Agora o 'this.usuarioService' vai funcionar porque a variável existe acima
        this.usuarioService.registrarNovoUsuario(data);
        return ResponseEntity.ok("Usuário registrado com sucesso!");
    }
}