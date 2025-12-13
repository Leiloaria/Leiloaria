package br.com.leiloaria.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.querydsl.core.types.Predicate;

import br.com.leiloaria.model.Usuario;

public interface UsuarioServiceI {
	Page<Usuario> listar(Predicate filtro, Pageable pageable);
    Usuario cadastrar(Usuario obj);
    Usuario atualizar(Long id, Usuario obj);
    void excluir(Long id);
    Usuario buscarPorId(Long id);
}
