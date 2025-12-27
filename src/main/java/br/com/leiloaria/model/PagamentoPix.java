package br.com.leiloaria.model;

import br.com.leiloaria.model.enums.BandeiraCartao;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PagamentoPix extends Pagamento{
	private String urlPagamento;
}
