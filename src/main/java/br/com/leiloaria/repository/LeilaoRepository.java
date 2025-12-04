package br.com.leiloaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.leiloaria.model.Leilao;

@Repository
public interface LeilaoRepository extends JpaRepository<Leilao, Long> {

}
