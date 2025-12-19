package br.com.leiloaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.leiloaria.controller.dto.lance.LanceRequest;
import br.com.leiloaria.controller.dto.lance.LanceResponse;
import br.com.leiloaria.controller.dto.leilao.LeilaoRequest;
import br.com.leiloaria.controller.dto.leilao.LeilaoResponse;
import br.com.leiloaria.controller.dto.leilao.UpdateLeilaoRequest;
import br.com.leiloaria.controller.dto.leilao.UpdateLeilaoStatusRequest;
import br.com.leiloaria.facade.Facade;
import br.com.leiloaria.model.Lance;
import br.com.leiloaria.model.Leilao;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/leiloes")
public class LeilaoController {
    @Autowired
	private Facade facade;
    
	@GetMapping("/{id}")
	public LeilaoResponse buscarPorId(@PathVariable("id") Long id) {
		Leilao l = facade.buscarLeilaoPorId(id);
		return new LeilaoResponse(l);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public LeilaoResponse cadastrarLeilao(@RequestBody @Valid LeilaoRequest obj) {
		Leilao l = facade.cadastrarLeilao(obj, obj.getIdUsuario());
		return new LeilaoResponse(l);
	}
	
	@PatchMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public LeilaoResponse atualizarCategoria(@PathVariable("id") Long idLeilao, @RequestBody @Valid UpdateLeilaoRequest obj) {
		Leilao l = facade.atualizarLeilao(idLeilao, obj);
		return new LeilaoResponse(l);
	}
	
	@PatchMapping("/{id}/status")
	@ResponseStatus(code = HttpStatus.OK)
	public void atualizarCategoriaStatus(@PathVariable("id") Long idLeilao, @RequestBody @Valid UpdateLeilaoStatusRequest obj) {
		facade.atualizarStatusLeilao(idLeilao, obj.getStatus());
	}
	
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void excluirLeilao(@PathVariable("id") Long id) {
        facade.excluirLeilao(id);
    }
}
