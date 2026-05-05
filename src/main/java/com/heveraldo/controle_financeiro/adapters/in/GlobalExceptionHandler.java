package com.heveraldo.controle_financeiro.adapters.in;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErroResposta> handleDatabaseError(SQLException ex) {
        ErroResposta erro = new ErroResposta("Erro de conexão com o banco de dados. Verifique sua senha.", 500);
        return ResponseEntity.status(500).body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> handleGeneralError(Exception ex) {
        ErroResposta erro = new ErroResposta("Ocorreu um erro inesperado: " + ex.getMessage(), 500);
        return ResponseEntity.status(500).body(erro);
    }
}