package br.com.leiloaria.model;

import java.util.List;

import br.com.leiloaria.model.enums.Condicao;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String descricao;
	@ElementCollection
	private List<String> imagens;
	
	@ManyToMany
	@JoinTable(
	        name = "item_categoria",
	        joinColumns = @JoinColumn(name = "item_id"),
	        inverseJoinColumns = @JoinColumn(name = "categoria_id")
	    )
	private List<Categoria> categorias;
	
	@Enumerated(EnumType.STRING)
    private Condicao condicao;
}
