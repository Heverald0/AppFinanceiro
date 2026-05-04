package com.heveraldo.controle_financeiro.core.service;

import com.heveraldo.controle_financeiro.core.model.Transacao;
import com.heveraldo.controle_financeiro.core.ports.FinanceiroServicePort;
import com.heveraldo.controle_financeiro.core.ports.TransacaoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FinanceiroService implements FinanceiroServicePort {

    private final TransacaoRepositoryPort repositoryPort;

    @Override
    public List<Transacao> buscarTodas() {
        return repositoryPort.buscarTodas();
    }

    @Override
    public void excluirTransacao(Long id) {
        // Ajuste aqui: verifique se no seu RepositoryPort o método chama 'excluir' ou 'deletar'
        repositoryPort.deletar(id); 
    }

    @Override
    public Transacao salvarTransacao(Transacao transacao) {
        return repositoryPort.salvar(transacao);
    }

    @Override
    public BigDecimal preverDecimoTerceiro(int ano) {
        // Implementação básica para limpar o erro de contrato
        List<Transacao> todas = repositoryPort.buscarTodas();
        // Lógica futura: filtrar receitas do ano e dividir por 12
        return BigDecimal.ZERO; 
    }
}