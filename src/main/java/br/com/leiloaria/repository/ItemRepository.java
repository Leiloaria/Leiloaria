package br.com.leiloaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.leiloaria.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
