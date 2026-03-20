package br.com.leiloaria.controller.dto.venda;

import java.math.BigDecimal;

import br.com.leiloaria.model.enums.BandeiraCartao;
import br.com.leiloaria.model.enums.FormaPagamento;
import br.com.leiloaria.model.enums.StatusPagamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateVendaRequest {
    @NotNull
    private FormaPagamento formaPagamento;
    
    //Campos Cartao de credito
	private String numeroCartao;
	private String nomeTitular;
	private BandeiraCartao bandeira;
	private Integer diaVencimento;
	private Integer anoVencimento;
}
