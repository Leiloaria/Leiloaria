package br.com.leiloaria.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.leiloaria.model.enums.FormaPagamento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime timestamp;
	@Enumerated(EnumType.STRING)
	private FormaPagamento formaPagamento;
	@Column(precision = 15, scale = 2)
	private BigDecimal valorTotal;
	@Column(precision = 15, scale = 2)
	private BigDecimal descontos;
	private int qtdParcelas;
}