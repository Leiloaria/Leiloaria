package br.com.leiloaria.repository;

import br.com.leiloaria.model.QUsuario;
import br.com.leiloaria.model.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, QuerydslPredicateExecutor<Usuario>,
	QuerydslBinderCustomizer<QUsuario>{
		
	@Override
	default void customize(QuerydslBindings bindings, QUsuario root) {
		bindings.bind(root.nome).first(
	           (path, value) -> path.containsIgnoreCase(value));
	}

	Optional<Usuario> findByEmail(String email);}
