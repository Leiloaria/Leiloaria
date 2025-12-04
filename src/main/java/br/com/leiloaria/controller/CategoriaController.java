package br.com.leiloaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import br.com.leiloaria.controller.dto.categoria.CategoriaRequest;
import br.com.leiloaria.controller.dto.categoria.CategoriaResponse;
import br.com.leiloaria.facade.Facade;
import br.com.leiloaria.model.Categoria;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    @Autowired
	private Facade facade;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public CategoriaResponse criarCategoria(@RequestBody 
											  @Valid CategoriaRequest obj) {
		Categoria c = obj.toModel();
		c = facade.cadastrarCategoria(c);
		return new CategoriaResponse(c);
	}
	
	@GetMapping("/{id}")
	public CategoriaResponse buscarPorId(@PathVariable("id") Long id) {
		Categoria c = facade.buscarCategoriaPorId(id);
		return new CategoriaResponse(c);
	}

    @GetMapping
    public Page<Categoria> listarCategorias(
            @QuerydslPredicate(root = Categoria.class) Predicate filtro,
            Pageable pageable) {
        return facade.listarCategorias(filtro, pageable);
    }

    @PatchMapping("/{id}")
    public CategoriaResponse atualizarCategoria(@PathVariable("id") Long id,
                                               @RequestBody @Valid CategoriaRequest obj) {
        Categoria c = obj.toModel();
        c = facade.atualizarCategoria(id, c);
        return new CategoriaResponse(c);
    }

    @PatchMapping("/{id}/subcategorias")
    public CategoriaResponse adicionarSubCategoria(@PathVariable("id") Long id,
                                                  @RequestBody @Valid CategoriaRequest obj) {
        Categoria subCategoria = obj.toModel();
        Categoria categoriaAtualizada = facade.addSubCategoria(id, subCategoria);
        return new CategoriaResponse(categoriaAtualizada);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void excluirCategoria(@PathVariable("id") Long id) {
        facade.excluirCategoria(id);
    }

}