package br.com.leiloaria.controller.dto.leilao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import br.com.leiloaria.controller.dto.items.UpdateItemRequest;
import br.com.leiloaria.model.Leilao;
import br.com.leiloaria.model.Lote;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 	
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLeilaoRequest {
	private LocalDateTime inicio;
	private LocalDateTime fim;
	private LocalDateTime prazoPagamento;
    
    @Size(min = 3)
	private String nome;
    
    @Positive
	private BigDecimal lanceMinimo;
    
    @Size(min = 3)
	private String descricao;
    
    @Valid
    private List<UpdateItemRequest> itens;

	public Leilao toModel() {
		Leilao leilao = new Leilao();
		leilao.setFim(fim);
		leilao.setInicio(inicio);
		leilao.setPrazoPagamento(prazoPagamento);
		Lote lote = new Lote();
		lote.setDescricao(descricao);
		lote.setNome(nome);
		lote.setLanceMinimo(lanceMinimo);
		leilao.setLote(lote);
		if (itens != null) {
			lote.setItens(
				itens.stream().map((x) -> x.toModel()).toList()
			);
		}
		return leilao;
	} 
	
}
