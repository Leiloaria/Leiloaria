package br.com.leiloaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.leiloaria.model.Lote;

@Repository
public interface LoteRepository extends JpaRepository<Lote, Long> {

}
