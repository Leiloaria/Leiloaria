package br.com.leiloaria.service.interfaces;

import br.com.leiloaria.controller.venda.VendaRequest;
import br.com.leiloaria.model.Pagamento;

public interface PagamentoServiceI {

	Pagamento gerarFormaPagamento(VendaRequest venda);

}