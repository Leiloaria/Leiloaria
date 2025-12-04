package br.com.leiloaria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.leiloaria.model.Categoria;
import br.com.leiloaria.repository.CategoriaRepository;
import br.com.leiloaria.service.exceptions.RecursoNaoEncontradoException;
import br.com.leiloaria.service.interfaces.CategoriaServiceI;
import com.querydsl.core.types.Predicate;

@Service
public class CategoriaService implements CategoriaServiceI {

    @Autowired
    private CategoriaRepository repository;
    
    @Override
    @Transactional(readOnly = true)
    public Page<Categoria> listar(Predicate filtro, Pageable pageable) {
        return repository.findAll(filtro, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Categoria buscarPorId(Long id) {
        Categoria categoria = repository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));
        return categoria;
    }

    @Override
    public Categoria cadastrar(Categoria obj) {
        return repository.save(obj);
    }

    @Override
    public Categoria atualizar(Long id, Categoria obj) {
        Categoria categoria = repository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));
        atualizarDados(categoria, obj);
        return repository.save(categoria);
    }

    private void atualizarDados(Categoria categoria, Categoria obj) {
        categoria.setNome(obj.getNome());
    }

    @Override
    public void excluir(Long id) {
        Categoria categoria = repository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));
        repository.delete(categoria);
    }

    @Override
    public Categoria addSubCategoria(Long id, Categoria obj) {
        Categoria categoria = repository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));
        categoria.addSubcategoria(obj);
        return repository.save(categoria);
    }
}
