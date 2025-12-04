package br.com.leiloaria.controller.dto.categoria;

import java.util.List;

import br.com.leiloaria.model.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaResponse {
    private Long id;
    private String nome;
    private List<CategoriaResponse> subcategorias;

    public CategoriaResponse(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
        if (categoria.getSubcategorias() != null)
            this.subcategorias = categoria.getSubcategorias().stream()
                .map(CategoriaResponse::new)
                .toList();
    }
}