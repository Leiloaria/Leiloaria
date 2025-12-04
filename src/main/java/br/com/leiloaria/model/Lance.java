package br.com.leiloaria.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime timestamp;
	@Column(precision = 15, scale = 2)
	private BigDecimal valor;

	@ManyToOne
	@JoinColumn(name = "lote_id")
	private Lote lote;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
}