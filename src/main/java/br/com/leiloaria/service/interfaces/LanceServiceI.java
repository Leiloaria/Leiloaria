package br.com.leiloaria.service.interfaces;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;

import br.com.leiloaria.model.Lance;

public interface LanceServiceI {

	Page<Lance> listar(Predicate filtro, Pageable pageable);

	Lance buscarPorId(Long id);

	Lance cadastrar(Lance obj);
	
	void excluir(Long id);
	
	Lance atualizarValor(Long id, BigDecimal novoValor);
	
	Lance buscarMaiorLance(Long loteId);
	
	List<Lance> buscarLancesPorLoteId(Long loteId); 
}