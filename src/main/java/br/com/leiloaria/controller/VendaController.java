package br.com.leiloaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.leiloaria.controller.dto.venda.UpdateVendaRequest;
import br.com.leiloaria.controller.dto.venda.VendaRequest;
import br.com.leiloaria.controller.dto.venda.VendaResponse;
import br.com.leiloaria.facade.Facade;
import br.com.leiloaria.model.Venda;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/vendas")
public class VendaController {
    @Autowired
	private Facade facade;
    
	@GetMapping("/{id}")
	public VendaResponse buscarPorId(@PathVariable("id") Long id) {
		Venda v = facade.buscarVendaPorId(id);
		return new VendaResponse(v);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public VendaResponse gerarVenda(@RequestBody @Valid VendaRequest obj) {
		Venda v = facade.gerarVenda(obj);
		return new VendaResponse(v);
	}
	
	@PatchMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public VendaResponse atualizarCategoria(@PathVariable("id") Long id, @RequestBody @Valid UpdateVendaRequest obj) {
		Venda v = facade.atualizarStatusPagamentoVenda(id, obj.getStatusPagamento());
		return new VendaResponse(v);
	}
}
