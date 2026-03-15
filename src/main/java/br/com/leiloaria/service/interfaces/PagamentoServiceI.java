package br.com.leiloaria.service.interfaces;

import br.com.leiloaria.controller.dto.venda.UpdateVendaRequest;
import br.com.leiloaria.model.Pagamento;

public interface PagamentoServiceI {

	Pagamento gerarFormaPagamento(UpdateVendaRequest venda);

}