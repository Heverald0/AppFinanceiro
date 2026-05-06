package com.heveraldo.controle_financeiro.core.ports;

import com.heveraldo.controle_financeiro.adapters.in.dto.response.ResumoResponseDTO;
import com.heveraldo.controle_financeiro.core.model.Transacao;
import java.util.List;
import java.math.BigDecimal;

public interface FinanceiroServicePort {
    List<Transacao> buscarTodas();
    void excluirTransacao(Long id);
    Transacao salvarTransacao(Transacao transacao);
    BigDecimal preverDecimoTerceiro(int mesesTrabalhados);
    ResumoResponseDTO calcularResumoMensal();
    List<Transacao> buscarPorUsuario(Long usuarioId);
}