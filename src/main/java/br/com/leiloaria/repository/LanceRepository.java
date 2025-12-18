package br.com.leiloaria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import br.com.leiloaria.model.Lance;

@Repository
public interface LanceRepository extends JpaRepository<Lance, Long>, QuerydslPredicateExecutor<Lance>{
	Optional<Lance> findTopByLoteIdOrderByValorDesc(Long loteId);
	List<Lance> findByLoteId(Long loteId);
}

