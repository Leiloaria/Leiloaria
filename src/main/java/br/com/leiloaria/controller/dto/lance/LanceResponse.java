package br.com.leiloaria.controller.dto.lance;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import br.com.leiloaria.controller.dto.categoria.CategoriaResponse;
import br.com.leiloaria.model.Categoria;
import br.com.leiloaria.model.Lance;
import br.com.leiloaria.model.Lote;
import br.com.leiloaria.model.Usuario;
import br.com.leiloaria.model.Venda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanceResponse {
    private Long id;
	private LocalDateTime timestamp;
	private BigDecimal valor;
	private Long loteId;
	private Long usuarioId;
	private Long vendaId;
    
    public LanceResponse(Lance lance) {
        this.id = lance.getId();
        this.timestamp = lance.getTimestamp();
        this.valor = lance.getValor();
        this.loteId = lance.getLote().getId();
        this.usuarioId = lance.getUsuario().getId();
        
        Venda venda = lance.getVenda();
        
        if(venda != null) {
        	this.vendaId = venda.getId();
        }
    }
}
