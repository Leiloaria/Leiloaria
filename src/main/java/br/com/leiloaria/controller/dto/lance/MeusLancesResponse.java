package br.com.leiloaria.controller.dto.lance;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.leiloaria.controller.dto.lote.MeusLancesLoteResponse;
import br.com.leiloaria.controller.dto.venda.VendaResponse;
import br.com.leiloaria.model.Lance;
import br.com.leiloaria.model.Venda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeusLancesResponse {
	private Long id;
	private LocalDateTime timestamp;
	private BigDecimal valor;
	private MeusLancesLoteResponse lote;
	private VendaResponse venda;
    
    public MeusLancesResponse(Lance lance) {
        this.id = lance.getId();
        this.timestamp = lance.getTimestamp();
        this.valor = lance.getValor();
        this.lote = new MeusLancesLoteResponse(lance.getLote());
        
        Venda venda = lance.getVenda();
        
        if(venda != null) {
        	this.venda = new VendaResponse(venda);
        }
    }
}
