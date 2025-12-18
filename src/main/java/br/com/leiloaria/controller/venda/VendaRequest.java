package br.com.leiloaria.controller.venda;

import java.math.BigDecimal;

import br.com.leiloaria.controller.lance.LanceRequest;
import br.com.leiloaria.model.enums.BandeiraCartao;
import br.com.leiloaria.model.enums.FormaPagamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendaRequest {
    @NotBlank
    @Positive
    private BigDecimal valor;
    
    @NotBlank
    private FormaPagamento formaPagamento;
    
    @NotBlank
    @Positive
    private Long lanceId;
    
    
    //Campos Cartao de credito
	private String numeroCartao;
	private String nomeTitular;
	private BandeiraCartao bandeira;
	private int diaVencimento;
	private int anoVencimento;
	
	//Campos Pix
	private String urlPagamento;
}
