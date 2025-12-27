package br.com.leiloaria.controller.dto.leilao;

import java.time.LocalDateTime;
import java.util.List;

import br.com.leiloaria.controller.dto.categoria.CategoriaResponse;
import br.com.leiloaria.model.Leilao;
import br.com.leiloaria.model.Lote;
import br.com.leiloaria.model.enums.StatusLeilao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeilaoResponse {
	private Long id;
	private StatusLeilao status;
	private LocalDateTime inicio;
	private LocalDateTime fim;
	private LocalDateTime prazoPagamento;
	
	private Lote lote;
	
    public LeilaoResponse(Leilao l) {
        this.id = l.getId();
        this.status = l.getStatus();
        this.inicio = l.getInicio();
        this.fim = l.getFim();
        this.prazoPagamento = l.getPrazoPagamento();
        this.lote = l.getLote();
    }
}
