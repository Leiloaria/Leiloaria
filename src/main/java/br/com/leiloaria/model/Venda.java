package br.com.leiloaria.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Venda {
	@Id
	private Long id;
	private LocalDateTime timestamp;
	private BigDecimal valor;
	@OneToOne
	private Lance lance;
}
