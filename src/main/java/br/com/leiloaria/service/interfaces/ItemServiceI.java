package br.com.leiloaria.service.interfaces;

import br.com.leiloaria.model.Item;

import java.util.List;

public interface ItemServiceI {
    List<Item> listar();
    Item cadastrar(Item obj);
    Item atualizar(Long id, Item obj);
    void excluir(Long id);
    Item buscarPorId(Long id);
}
