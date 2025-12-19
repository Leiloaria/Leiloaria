package br.com.leiloaria.controller.dto.items;

import java.util.List;

import br.com.leiloaria.model.enums.Condicao;
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
public class UpdateItemRequest {
	@NotNull
    @Positive
	private Long idItem;
	
    @Size(min = 3)
    private String nome;

    @Size(min = 3)
    private String descricao;
    
    private Condicao condicao;
    
    private List<String> imagens;
    
    private List<Long> categoriasId;
}
