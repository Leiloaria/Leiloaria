package br.com.leiloaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import br.com.leiloaria.model.Venda;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long>, QuerydslPredicateExecutor<Venda> {

}
