package br.com.leiloaria.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;

import br.com.leiloaria.model.Categoria;
import br.com.leiloaria.model.Lance;
import br.com.leiloaria.model.Lote;
import br.com.leiloaria.repository.LanceRepository;
import br.com.leiloaria.service.exceptions.AtualizarLanceInvalidoException;
import br.com.leiloaria.service.exceptions.RecursoNaoEncontradoException;
import br.com.leiloaria.service.interfaces.LanceServiceI;

@Service
public class LanceService implements LanceServiceI {
	
    @Autowired
    private LanceRepository repository;
    
    @Override
    @Transactional(readOnly = true)
    public Page<Lance> listar(Predicate filtro, Pageable pageable) {
        return repository.findAll(filtro, pageable);
    }
	
    @Override
    @Transactional(readOnly = true)
	public Lance buscarPorId(Long id) {
        Lance lance = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Lance não encontrado"));
            return lance;
	}
    
    @Override
    public Lance cadastrar(Lance obj) {
        return repository.save(obj);
    }
    
    @Override
    public Lance atualizarValor(Long id, BigDecimal novoValor) {
        Lance lance = repository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Lance não encontrado"));

        if(lance.getValor().compareTo(novoValor) > 0) {
        	throw new AtualizarLanceInvalidoException("Novo valor é menor que o anterior");
        }
        
        lance.setValor(novoValor);
        
        return repository.save(lance);
    }
    
    @Override
    public void excluir(Long id) {
        repository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
	public Lance buscarMaiorLance(Long loteId) {              
        Lance lance = repository.findTopByLoteIdOrderByValorDesc(loteId)
        	.orElseThrow(() -> new RecursoNaoEncontradoException("Lance não encontrado"));
        
        return lance;
	}

    @Override
    @Transactional(readOnly = true)
	public List<Lance> buscarLancesPorLoteId(Long loteId) {              
        List<Lance> lances = repository.findByLoteId(loteId);
        
        if(lances.size() == 0 ) {
        	throw new RecursoNaoEncontradoException("Lances não encontrado para esse lote");
        }
        
        return lances;
	}
}
