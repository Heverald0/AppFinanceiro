package com.heveraldo.controle_financeiro.infrastructure.config;

import com.heveraldo.controle_financeiro.adapters.out.UsuarioEntity;
import com.heveraldo.controle_financeiro.adapters.out.SpringDataUsuarioRepository; 
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SpringDataUsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioEntity usuario = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        return new User(usuario.getEmail(), usuario.getSenha(), new ArrayList<>());
    }
}