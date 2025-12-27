package br.com.leiloaria.model;

import br.com.leiloaria.model.enums.BandeiraCartao;
import br.com.leiloaria.model.enums.StatusPagamento;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PagamentoCartaoCredito extends Pagamento{
	private String numeroCartao;
	private String nomeTitular;
	private BandeiraCartao bandeira;
	private int diaVencimento;
	private int anoVencimento;
}
