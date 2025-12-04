package br.com.leiloaria.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Endereco {
	@Id
	private Long id;
	private String rua;
	private String logradouro;
	private String bairro;
	private String cidade;
	private String cep;
	private String estado;
}
