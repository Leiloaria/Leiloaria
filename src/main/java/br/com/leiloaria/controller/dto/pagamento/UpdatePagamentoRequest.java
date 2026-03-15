package br.com.leiloaria.controller.dto.pagamento;

import br.com.leiloaria.model.enums.StatusPagamento;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePagamentoRequest {
	@NotBlank
    private StatusPagamento statusPagamento;
}
