package br.com.leiloaria.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import br.com.leiloaria.model.Leilao;
import br.com.leiloaria.model.enums.StatusLeilao;

@Repository
public interface LeilaoRepository extends JpaRepository<Leilao, Long>, QuerydslPredicateExecutor<Leilao> {
	List<Leilao> findByProprietarioId(Long usuarioId);
	List<Leilao> findByStatusAndInicioBefore(StatusLeilao status, LocalDateTime data);
	List<Leilao> findByStatusAndFimBefore(StatusLeilao status, LocalDateTime data);
	List<Leilao> findByStatusAndPrazoPagamentoBefore(StatusLeilao status, LocalDateTime data);
	
    Page<Leilao> findByLoteLancesUsuarioId(Long usuarioId, Pageable pageable);
}
