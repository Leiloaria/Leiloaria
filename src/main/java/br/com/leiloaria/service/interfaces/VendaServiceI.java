package br.com.leiloaria.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Predicate;

import br.com.leiloaria.model.Lance;
import br.com.leiloaria.model.Pagamento;
import br.com.leiloaria.model.Venda;
import br.com.leiloaria.model.enums.StatusPagamento;

public interface VendaServiceI {

	Venda gerarVenda(Lance lanceVencedor, Pagamento pagamento);
	Venda buscarPorId(Long id);
	Page<Venda> listar(Predicate filtro, Pageable pageable);
	Venda atualizarStatusPagamentoVenda(Long vendaId, StatusPagamento novoStatus);
}