package com.heveraldo.controle_financeiro.adapters.in.controller;

import com.heveraldo.controle_financeiro.adapters.in.dto.request.TransacaoRequestDTO;
import com.heveraldo.controle_financeiro.adapters.in.dto.response.ResumoResponseDTO;
import com.heveraldo.controle_financeiro.adapters.in.dto.response.TransacaoResponseDTO;
import com.heveraldo.controle_financeiro.core.model.Transacao;
import com.heveraldo.controle_financeiro.core.ports.TransacaoRepositoryPort;
import com.heveraldo.controle_financeiro.core.service.FinanceiroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transacoes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class FinanceiroController {

    private final FinanceiroService financeiroService;
    private final TransacaoRepositoryPort repository;

    @PostMapping("/transacoes")
    public ResponseEntity<TransacaoResponseDTO> criar(@Valid @RequestBody TransacaoRequestDTO request) {
        // Em Records, acessamos como descricao(), valor(), etc. (sem o prefixo 'get')
        Transacao transacao = Transacao.builder()
                .descricao(request.descricao())
                .valor(request.valor())
                .data(request.data())
                .tipo(request.tipo())
                .categoria(request.categoria())
                .observacao(request.observacao())
                .usuarioId(request.usuarioId()) 
                .build();
        
        Transacao salva = repository.salvar(transacao);
        
        return ResponseEntity.ok(toResponse(salva));
    }

    @GetMapping("/previsao-13/{ano}")
    public ResponseEntity<BigDecimal> obterPrevisao13(@PathVariable int ano) {
        return ResponseEntity.ok(financeiroService.preverDecimoTerceiro(ano));
    }

    @GetMapping("/transacoes")
    public ResponseEntity<List<TransacaoResponseDTO>> listarTodas() {
        List<TransacaoResponseDTO> lista = repository.buscarTodas()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/resumo")
    public ResponseEntity<ResumoResponseDTO> getResumo() {
    return ResponseEntity.ok(financeiroService.calcularResumoMensal());
}

    private TransacaoResponseDTO toResponse(Transacao t) {
        return new TransacaoResponseDTO(
            t.getId(),
            t.getDescricao(),
            t.getValor(),
            t.getData(),
            t.getTipo(),
            t.getCategoria(),
            t.getObservacao()
        );
    }
}