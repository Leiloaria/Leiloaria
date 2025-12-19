package br.com.leiloaria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import br.com.leiloaria.model.Leilao;

@Repository
public interface LeilaoRepository extends JpaRepository<Leilao, Long>, QuerydslPredicateExecutor<Leilao> {
	List<Leilao> findByProprietarioId(Long usuarioId);
}
