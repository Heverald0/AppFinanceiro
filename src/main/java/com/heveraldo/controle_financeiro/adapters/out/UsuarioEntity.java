package com.heveraldo.controle_financeiro.adapters.out;

import com.fasterxml.jackson.annotation.JsonIgnore; // Importante para segurança
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonIgnore // Impede que a senha seja enviada em qualquer resposta JSON para o React
    @ToString.Exclude // Impede que a senha apareça em logs de erro ou de sistema
    private String senha;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @ToString.Exclude // Evita recursividade infinita no log (Usuario -> Transacao -> Usuario)
    private List<TransacaoEntity> transacoes;
}