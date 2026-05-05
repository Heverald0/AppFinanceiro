package com.heveraldo.controle_financeiro.adapters.in.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CategoriaResumoDTO {
    private String name; // 'name' para bater com o Recharts do React
    private BigDecimal value;
}