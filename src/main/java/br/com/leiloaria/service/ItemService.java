package br.com.leiloaria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.leiloaria.model.Item;
import br.com.leiloaria.repository.ItemRepository;
import br.com.leiloaria.service.exceptions.RecursoNaoEncontradoException;
import br.com.leiloaria.service.interfaces.ItemServiceI;

@Service
public class ItemService implements ItemServiceI {

    @Autowired
    private ItemRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Item> listar() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Item buscarPorId(Long id) {
        Item item = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Item não encontrada"));
        return item;
    }

    @Override
    public Item cadastrar(Item obj) {
        return repository.save(obj);
    }

    @Override
    public Item atualizar(Long id, Item obj) {
        Item item = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Item não encontrada"));
        atualizarDados(item, obj);
        return repository.save(item);
    }

    private void atualizarDados(Item item, Item obj) {
        if (obj.getNome() != null) {
            item.setNome(obj.getNome());
        }

        if (obj.getDescricao() != null) {
            item.setDescricao(obj.getDescricao());
        }

        if (obj.getImagens() != null) {
            item.setImagens(obj.getImagens());
        }

        if (obj.getCondicao() != null) {
            item.setCondicao(obj.getCondicao());
        }

    }

    @Override
    public void excluir(Long id) {
        Item item = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Item não encontrada"));
        repository.delete(item);
    }

}
