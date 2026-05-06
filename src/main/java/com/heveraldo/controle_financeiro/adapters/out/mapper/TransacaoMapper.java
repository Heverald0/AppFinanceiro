package com.heveraldo.controle_financeiro.adapters.out.mapper;

import com.heveraldo.controle_financeiro.adapters.out.TransacaoEntity;
import com.heveraldo.controle_financeiro.core.model.Transacao;
import com.heveraldo.controle_financeiro.core.model.TipoTransacao; // Importe seu Enum
import org.springframework.stereotype.Component;

@Component
public class TransacaoMapper {

    public Transacao toDomain(TransacaoEntity entity) {
        if (entity == null) return null;
        
        Transacao domain = new Transacao();
        domain.setId(entity.getId());
        domain.setDescricao(entity.getDescricao());
        domain.setValor(entity.getValor());
        domain.setData(entity.getData());
        domain.setUsuarioId(entity.getUsuarioId());

        if (entity.getTipo() != null) {
            domain.setTipo(TipoTransacao.valueOf(entity.getTipo().toUpperCase()));
        }
        
        return domain;
    }

    public TransacaoEntity toEntity(Transacao domain) {
        if (domain == null) return null;
        
        TransacaoEntity entity = new TransacaoEntity();
        entity.setId(domain.getId());
        entity.setDescricao(domain.getDescricao());
        entity.setValor(domain.getValor());
        entity.setData(domain.getData());
        entity.setUsuarioId(domain.getUsuarioId());

        if (domain.getTipo() != null) {
            entity.setTipo(domain.getTipo().name());
        }
        
        return entity;
    }
}