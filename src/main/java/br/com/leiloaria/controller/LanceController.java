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

import br.com.leiloaria.controller.lance.LanceRequest;
import br.com.leiloaria.controller.lance.LanceResponse;
import br.com.leiloaria.facade.Facade;
import br.com.leiloaria.model.Lance;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/lances")
public class LanceController {
    @Autowired
	private Facade facade;

	@PostMapping("/{id}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public LanceResponse criarCategoria(@RequestBody @Valid LanceRequest obj) {
		Lance l = facade.criarLance(obj);
		return new LanceResponse(l);
	}
	
	@PatchMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public LanceResponse atualizarCategoria(@PathVariable("id") Long id, @RequestBody @Valid LanceRequest obj) {
		Lance l = facade.atualizarValor(id, obj.getValor());
		return new LanceResponse(l);
	}
	
	@GetMapping("/{id}")
	public LanceResponse buscarPorId(@PathVariable("id") Long id) {
		Lance l = facade.buscarLancePorId(id);
		return new LanceResponse(l);
	}
	
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void excluirLance(@PathVariable("id") Long id) {
        facade.excluirLance(id);
    }
}
