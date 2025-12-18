package br.com.leiloaria.controller.venda;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.leiloaria.controller.lance.LanceResponse;
import br.com.leiloaria.model.Lance;
import br.com.leiloaria.model.Lote;
import br.com.leiloaria.model.Pagamento;
import br.com.leiloaria.model.Usuario;
import br.com.leiloaria.model.Venda;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendaResponse {
	private Long id;
	private BigDecimal valor;
	private Pagamento metodoPagamento;
	private Lance lance;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
    public VendaResponse(Venda v) {
        this.id = v.getId();
        this.valor = v.getValor();
        this.metodoPagamento = v.getMetodoPagamento();
        this.lance = v.getLance();
        this.createdAt = v.getCreatedAt();
        this.updatedAt = v.getUpdatedAt();
    }
}
