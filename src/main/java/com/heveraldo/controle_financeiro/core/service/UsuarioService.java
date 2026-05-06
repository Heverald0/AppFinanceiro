package com.heveraldo.controle_financeiro.core.service;

import com.heveraldo.controle_financeiro.adapters.out.UsuarioEntity;
import com.heveraldo.controle_financeiro.adapters.out.SpringDataUsuarioRepository; 
import com.heveraldo.controle_financeiro.adapters.in.dto.request.UsuarioRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final SpringDataUsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public void registrarNovoUsuario(UsuarioRequestDTO dto) {
    // Em Records, usamos dto.senha() em vez de dto.getSenha()
    String senhaCriptografada = passwordEncoder.encode(dto.senha());

    UsuarioEntity novoUsuario = UsuarioEntity.builder()
            .nome(dto.nome())   // Use nome()
            .email(dto.email()) // Use email()
            .senha(senhaCriptografada)
            .build();

    repository.save(novoUsuario);
}

    public Long buscarIdPorEmail(String email) {
    return repository.findByEmail(email)
            .map(UsuarioEntity::getId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
}
}