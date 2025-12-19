package br.com.leiloaria.controller.dto.items;

import java.util.List;

import br.com.leiloaria.model.Categoria;
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
public class ItemRequest {
    @Positive
	private Long idItem;
	
    @NotBlank
    @Size(min = 3)
    private String nome;

    @Size(min = 3)
    private String descricao;
    
    @NotNull
    private Condicao condicao;
    
    private List<String> imagens;
    
    @NotEmpty
    private List<Long> categoriasId;
}
