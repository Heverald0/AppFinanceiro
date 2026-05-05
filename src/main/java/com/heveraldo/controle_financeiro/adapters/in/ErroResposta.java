package com.heveraldo.controle_financeiro.adapters.in;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErroResposta {
    private String mensagem;
    private int status;
}