package com.heveraldo.controle_financeiro.adapters.out;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transacoes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;

    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    private String categoria;
    private String tipo;
    private String observacao;

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    @Column(columnDefinition = "BIT(1) DEFAULT 0")
    private boolean deletado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;
}