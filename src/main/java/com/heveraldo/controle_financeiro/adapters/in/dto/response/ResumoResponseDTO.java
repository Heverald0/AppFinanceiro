package com.heveraldo.controle_financeiro.adapters.in.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ResumoResponseDTO {
    private BigDecimal saldoTotal;
    private BigDecimal receitasMes;
    private BigDecimal despesasMes;
    private List<CategoriaResumoDTO> gastosPorCategoria;
}