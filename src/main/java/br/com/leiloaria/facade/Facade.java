package br.com.leiloaria.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import br.com.leiloaria.model.Categoria;
import br.com.leiloaria.service.interfaces.CategoriaServiceI;

@Service
public class Facade {
    @Autowired
    private CategoriaServiceI categoriaService;

    public Page<Categoria> listarCategorias(Predicate filtro, Pageable pageable){
        return categoriaService.listar(filtro, pageable);
    }
    public Categoria cadastrarCategoria(Categoria obj){
        return categoriaService.cadastrar(obj);
    }
    public Categoria atualizarCategoria(Long id, Categoria obj){
        return categoriaService.atualizar(id, obj);
    }
    public void excluirCategoria(Long id){
        categoriaService.excluir(id);
    }
    public Categoria buscarCategoriaPorId(Long id){
        return categoriaService.buscarPorId(id);
    }

    public Categoria addSubCategoria(Long id, Categoria obj){
        return categoriaService.addSubCategoria(id, obj);
    }
}
