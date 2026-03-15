package br.com.leiloaria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leiloaria.controller.dto.venda.CreateVendaRequest;
import br.com.leiloaria.controller.dto.venda.UpdateVendaRequest;
import br.com.leiloaria.model.Pagamento;
import br.com.leiloaria.model.PagamentoCartaoCredito;
import br.com.leiloaria.model.PagamentoPix;
import br.com.leiloaria.model.enums.FormaPagamento;
import br.com.leiloaria.model.enums.StatusPagamento;
import br.com.leiloaria.repository.LanceRepository;
import br.com.leiloaria.repository.PagamentoRepository;
import br.com.leiloaria.service.exceptions.FormaPagamentoInvalidaException;
import br.com.leiloaria.service.interfaces.PagamentoServiceI;
import java.util.UUID;

@Service
public class PagamentoService implements PagamentoServiceI {
    
    @Override
	public Pagamento gerarFormaPagamento(UpdateVendaRequest venda) {
    	Pagamento pagamento = null;
    	
    	switch(venda.getFormaPagamento()) {
    		case FormaPagamento.CREDITO:
    			PagamentoCartaoCredito pagamentoCartaoCredito = new PagamentoCartaoCredito();
    			pagamentoCartaoCredito.setStatus(StatusPagamento.PENDING);
    			pagamentoCartaoCredito.setDiaVencimento(venda.getDiaVencimento());
    			pagamentoCartaoCredito.setAnoVencimento(venda.getAnoVencimento());
    			pagamentoCartaoCredito.setBandeira(venda.getBandeira());
    			pagamentoCartaoCredito.setNomeTitular(venda.getNomeTitular());
    			pagamentoCartaoCredito.setNumeroCartao(venda.getNumeroCartao());
    			
    			pagamento = pagamentoCartaoCredito;
    			break;
    		case FormaPagamento.PIX:
    			PagamentoPix pagamentoPix = new PagamentoPix();
    			pagamentoPix.setStatus(StatusPagamento.PENDING);
    			pagamentoPix.setChavePix(""+UUID.randomUUID()+UUID.randomUUID()+UUID.randomUUID()+UUID.randomUUID());
    			
    			pagamento = pagamentoPix;
    			break;
    		default:
    			throw new FormaPagamentoInvalidaException("Forma de Pagamento não é aceita");
    	}
    	
    	return pagamento;
    }
}
