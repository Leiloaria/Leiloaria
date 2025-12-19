package br.com.leiloaria.controller.dto.leilao;

import br.com.leiloaria.model.enums.StatusLeilao;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLeilaoStatusRequest {
	@NotNull
	private StatusLeilao status;
}
