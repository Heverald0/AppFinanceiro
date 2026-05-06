package com.heveraldo.controle_financeiro.adapters.in.controller;

import com.heveraldo.controle_financeiro.core.model.Transacao;
import com.heveraldo.controle_financeiro.core.ports.FinanceiroServicePort;
import com.heveraldo.controle_financeiro.core.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class TransacaoController {

    private final FinanceiroServicePort servicePort;
    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Transacao>> listarTodas() {
        
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Long usuarioId = usuarioService.buscarIdPorEmail(email);

        // Agora filtramos as transações apenas deste usuário
        return ResponseEntity.ok(servicePort.buscarPorUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<Transacao> salvar(@RequestBody Transacao transacao) {
        
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Long usuarioId = usuarioService.buscarIdPorEmail(email);

        
        transacao.setUsuarioId(usuarioId); 
        
        return ResponseEntity.ok(servicePort.salvarTransacao(transacao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        servicePort.excluirTransacao(id);
        return ResponseEntity.noContent().build();
    }
}