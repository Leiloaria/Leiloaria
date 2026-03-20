package br.com.leiloaria.controller.dto.lote;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.leiloaria.controller.dto.lance.LanceResponse;
import br.com.leiloaria.controller.dto.leilao.LeilaoResponse;
import br.com.leiloaria.model.Item;
import br.com.leiloaria.model.Lance;
import br.com.leiloaria.model.Lote;
import br.com.leiloaria.model.enums.StatusLeilao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeusLancesLoteResponse {
	private Long id;
	private String nome;
	private String descricao;
	private List<Item> itens;
	private StatusLeilao status;
	private LocalDateTime fim;
	private LocalDateTime prazoPagamento;
	
	public MeusLancesLoteResponse(Lote l) {
        this.id = l.getId();
        this.nome = l.getNome();
        this.descricao = l.getDescricao();
        this.itens = l.getItens();
        this.status = l.getLeilao().getStatus();
        this.fim = l.getLeilao().getFim();
        this.prazoPagamento = l.getLeilao().getPrazoPagamento();
    }
}
