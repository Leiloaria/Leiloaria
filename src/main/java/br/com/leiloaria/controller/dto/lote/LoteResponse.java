package br.com.leiloaria.controller.dto.lote;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.leiloaria.controller.dto.lance.LanceResponse;
import br.com.leiloaria.controller.dto.leilao.LeilaoResponse;
import br.com.leiloaria.model.Item;
import br.com.leiloaria.model.Lance;
import br.com.leiloaria.model.Leilao;
import br.com.leiloaria.model.Lote;
import br.com.leiloaria.model.enums.StatusLeilao;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoteResponse {
	private Long id;
	private String nome;
	private String descricao;
	private BigDecimal lanceMinimo;
	private List<Item> itens;
    private LeilaoResponse leilao;
	private List<LanceResponse> lances;
	
	public LoteResponse(Lote l) {
        this.id = l.getId();
        this.nome = l.getNome();
        this.descricao = l.getDescricao();
        this.lanceMinimo = l.getLanceMinimo();
        this.itens = l.getItens();
        this.lances = new ArrayList<>();
        List<Lance> lances = l.getLances();
        
        if(lances != null) {
        	for (Lance lance : lances) {
    			this.lances.add(new LanceResponse(lance));
    		}
        }
    }
}
