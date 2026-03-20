package br.com.leiloaria.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

import br.com.leiloaria.model.Avaliacao;
import br.com.leiloaria.model.QAvaliacao;

@Repository
public interface AvaliacaoRepository
    extends JpaRepository<Avaliacao, Long>, QuerydslPredicateExecutor<Avaliacao> {

  boolean existsByUsuarioIdAndLoteId(Long idUsuario, Long idLote);

  List<Avaliacao> findAllByLoteId(Long idLote);

  default Page<Avaliacao> findAllByLoteIdWithPredicate(Long idLote, Predicate predicate, Pageable pageable) {
    QAvaliacao qAvaliacao = QAvaliacao.avaliacao;
    BooleanBuilder filtro = new BooleanBuilder();
    
    filtro.and(qAvaliacao.lote.id.eq(idLote));

    if (predicate != null) {
      filtro.and(predicate);
    }
    return findAll(filtro, pageable);
  }
}
