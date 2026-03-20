package br.com.leiloaria.controller.dto.pagamento;

import br.com.leiloaria.model.enums.StatusPagamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePagamentoRequest {
	@NotNull
    private StatusPagamento statusPagamento;
}
