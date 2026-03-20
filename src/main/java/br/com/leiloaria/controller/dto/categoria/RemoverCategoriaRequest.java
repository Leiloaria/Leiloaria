package br.com.leiloaria.controller.dto.categoria;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoverCategoriaRequest {
	@NotNull
    private Long userId;
}
