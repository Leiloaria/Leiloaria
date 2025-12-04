package br.com.leiloaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import br.com.leiloaria.model.Categoria;
import br.com.leiloaria.model.QCategoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>, QuerydslPredicateExecutor<Categoria>,
	QuerydslBinderCustomizer<QCategoria>{

	@Override
	default void customize(QuerydslBindings bindings, QCategoria root) {
		bindings.bind(root.nome).first(
            (path, value) -> path.containsIgnoreCase(value));
	}}
