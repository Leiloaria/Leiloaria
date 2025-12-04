package br.com.leiloaria.service.interfaces;

import br.com.leiloaria.model.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.querydsl.core.types.Predicate;

public interface CategoriaServiceI {
    Page<Categoria> listar(Predicate filtro, Pageable pageable);
    Categoria cadastrar(Categoria obj);
    Categoria atualizar(Long id, Categoria obj);
    Categoria addSubCategoria(Long id, Categoria obj);
    void excluir(Long id);
    Categoria buscarPorId(Long id);
}
