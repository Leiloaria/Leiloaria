package br.com.leiloaria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;

import br.com.leiloaria.model.Lance;
import br.com.leiloaria.model.Pagamento;
import br.com.leiloaria.model.Venda;
import br.com.leiloaria.model.enums.StatusPagamento;
import br.com.leiloaria.repository.VendaRepository;
import br.com.leiloaria.service.exceptions.RecursoNaoEncontradoException;
import br.com.leiloaria.service.interfaces.VendaServiceI;

@Service
public class VendaService implements VendaServiceI {
	
    @Autowired
    private VendaRepository repository;
	
    @Override
	public Venda gerarVenda(Lance lanceVencedor, Pagamento pagamento) {
    	Venda novaVenda = new Venda();
    	novaVenda.setLance(lanceVencedor);
    	novaVenda.setValor(lanceVencedor.getValor());
    	novaVenda.setMetodoPagamento(pagamento);
    	
    	return repository.save(novaVenda);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Venda> listar(Predicate filtro, Pageable pageable) {
        return repository.findAll(filtro, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
	public Venda buscarPorId(Long id) {
        Venda venda = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Venda não encontrada"));
            return venda;
	}
    
    @Override
    public Venda atualizarStatusPagamentoVenda(Long vendaId, StatusPagamento novoStatus) {
        Venda venda = repository.findById(vendaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Venda não encontrada"));
        
        Pagamento pagamento = venda.getMetodoPagamento();
        pagamento.setStatus(novoStatus);
        
        venda.setMetodoPagamento(pagamento);
        
        return repository.save(venda);
    }
}
