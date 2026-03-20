package br.com.leiloaria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leiloaria.model.Lote;
import br.com.leiloaria.repository.LoteRepository;
import br.com.leiloaria.service.exceptions.RecursoNaoEncontradoException;
import br.com.leiloaria.service.interfaces.LoteServiceI;

@Service
public class LoteService implements LoteServiceI {
    @Autowired
    private LoteRepository repository;

    @Override
    public Lote buscarLoteById(Long id) {
        Lote lote = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Lote não encontrado"));

        return lote;
    }

    @Override
    public Lote atualizarLote(Long id, Lote obj) {
        Lote lote = buscarLoteById(id);
        atualizarDados(lote, obj);
        return repository.save(lote);
    }

    private void atualizarDados(Lote lote, Lote obj) {
        if (obj.getNome() != null) {
            lote.setNome(obj.getNome());
        }

        if (obj.getDescricao() != null) {
            lote.setDescricao(obj.getDescricao());
        }

        if (obj.getLanceMinimo() != null) {
            lote.setLanceMinimo(obj.getLanceMinimo());
        }

        if (obj.getItens() != null && !obj.getItens().isEmpty()) {
            lote.setItens(obj.getItens());
        }
    }
}
