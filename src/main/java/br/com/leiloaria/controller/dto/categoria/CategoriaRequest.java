package br.com.leiloaria.controller.dto.categoria;

import br.com.leiloaria.model.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaRequest {
    @NotBlank
    @Size(min = 3)
    private String nome;

    public Categoria toModel() {
        return new Categoria(null, nome, null);
    }
}
