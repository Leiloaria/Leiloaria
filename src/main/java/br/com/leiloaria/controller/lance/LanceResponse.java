package br.com.leiloaria.controller.lance;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import br.com.leiloaria.controller.dto.categoria.CategoriaResponse;
import br.com.leiloaria.model.Categoria;
import br.com.leiloaria.model.Lance;
import br.com.leiloaria.model.Lote;
import br.com.leiloaria.model.Usuario;
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
	private Lote lote;
	private Usuario usuario;
    
    public LanceResponse(Lance lance) {
        this.id = lance.getId();
        this.timestamp = lance.getTimestamp();
        this.valor = lance.getValor();
        this.lote = lance.getLote();
        this.usuario = lance.getUsuario();
    }
}
