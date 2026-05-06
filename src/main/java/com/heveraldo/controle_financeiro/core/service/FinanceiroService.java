package com.heveraldo.controle_financeiro.core.service;

import com.heveraldo.controle_financeiro.adapters.in.dto.response.CategoriaResumoDTO;
import com.heveraldo.controle_financeiro.adapters.in.dto.response.ResumoResponseDTO;
import com.heveraldo.controle_financeiro.core.model.Transacao;
import com.heveraldo.controle_financeiro.core.ports.FinanceiroServicePort;
import com.heveraldo.controle_financeiro.core.ports.TransacaoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FinanceiroService implements FinanceiroServicePort {

    private final TransacaoRepositoryPort repositoryPort;
    private final TransacaoRepositoryPort transacaoRepository;

    @Override
    public Transacao salvarTransacao(Transacao transacao) {
        return repositoryPort.salvar(transacao);
    }

    @Override
    public List<Transacao> buscarTodas() {
        return repositoryPort.buscarTodas();
    }

    @Override
    public void excluirTransacao(Long id) {
        repositoryPort.deletar(id);
    }

    @Override
    public List<Transacao> buscarPorUsuario(Long usuarioId) {
    // Aqui você chama o seu repositório de transações passando o ID do usuário
        return transacaoRepository.findByUsuarioId(usuarioId);
}

    @Override
    public ResumoResponseDTO calcularResumoMensal() {
        List<Transacao> todas = repositoryPort.buscarTodas();

        BigDecimal receitas = todas.stream()
                .filter(t -> t.getTipo() != null && t.getTipo().name().trim().equalsIgnoreCase("RECEITA"))
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal despesas = todas.stream()
                .filter(t -> t.getTipo() != null && t.getTipo().name().trim().equalsIgnoreCase("DESPESA"))
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, BigDecimal> porCategoria = todas.stream()
                .filter(t -> t.getTipo() != null && t.getTipo().name().trim().equalsIgnoreCase("DESPESA"))
                .collect(Collectors.groupingBy(
                        t -> t.getCategoria() != null ? t.getCategoria().name() : "OUTROS",
                        Collectors.mapping(Transacao::getValor, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
                ));

        List<CategoriaResumoDTO> categoriasDTO = porCategoria.entrySet().stream()
                .map(entry -> new CategoriaResumoDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return ResumoResponseDTO.builder()
                .saldoTotal(receitas.subtract(despesas))
                .receitasMes(receitas)
                .despesasMes(despesas)
                .gastosPorCategoria(categoriasDTO)
                .build();
    }

    @Override
    public BigDecimal preverDecimoTerceiro(int ano) {
        List<Transacao> todas = repositoryPort.buscarTodas();
        
        BigDecimal totalReceitas = todas.stream()
                .filter(t -> t.getTipo() != null && t.getTipo().name().trim().equalsIgnoreCase("RECEITA"))
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalReceitas.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;

        return totalReceitas.divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);
    }
}