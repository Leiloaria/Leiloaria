package br.com.leiloaria.controller.dto.leilao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import br.com.leiloaria.controller.dto.items.ItemRequest;
import br.com.leiloaria.controller.dto.items.UpdateItemRequest;
import br.com.leiloaria.model.enums.StatusLeilao;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
}
