package br.com.leiloaria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.leiloaria.model.Lance;
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
}
