package br.com.leiloaria.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.leiloaria.model.Lance;
import br.com.leiloaria.model.Lote;

@Repository
public interface LoteRepository extends JpaRepository<Lote, Long> {
}
