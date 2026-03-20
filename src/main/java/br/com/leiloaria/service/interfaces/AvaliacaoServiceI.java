package br.com.leiloaria.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Predicate;
import br.com.leiloaria.model.Avaliacao;

public interface AvaliacaoServiceI {

    Avaliacao avaliarCompra(Avaliacao avaliacao);
    Page<Avaliacao> listarPorLote(Long idLote, Predicate predicate, Pageable pageable);
    Double calcularMediaPorLote(Long idLote); // vai para controller de lote?
    Avaliacao buscarPorId(Long id);
    Page<Avaliacao> listar(Predicate predicate, Pageable pageable);
    Avaliacao atualizar(Long id, Avaliacao avaliacao);
    void excluir(Long id);
    
}
