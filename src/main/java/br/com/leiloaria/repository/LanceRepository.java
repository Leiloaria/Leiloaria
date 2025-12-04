package br.com.leiloaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.leiloaria.model.Lance;

@Repository
public interface LanceRepository extends JpaRepository<Lance, Long> {

}
