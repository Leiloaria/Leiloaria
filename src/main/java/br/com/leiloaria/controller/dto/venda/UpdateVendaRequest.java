package br.com.leiloaria.controller.dto.venda;

import java.math.BigDecimal;

import br.com.leiloaria.model.enums.BandeiraCartao;
import br.com.leiloaria.model.enums.FormaPagamento;
import br.com.leiloaria.model.enums.StatusPagamento;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateVendaRequest {
    @NotBlank
    private StatusPagamento statusPagamento;
}
