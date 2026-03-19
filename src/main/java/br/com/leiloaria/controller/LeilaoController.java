package br.com.leiloaria.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
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

import br.com.leiloaria.controller.dto.leilao.CancelarLeilaoRequest;
import br.com.leiloaria.controller.dto.leilao.LeilaoRequest;
import br.com.leiloaria.controller.dto.leilao.LeilaoResponse;
import br.com.leiloaria.controller.dto.leilao.UpdateLeilaoRequest;
import br.com.leiloaria.facade.Facade;
import br.com.leiloaria.model.Leilao;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/leiloes")
public class LeilaoController {

    @Autowired
	private Facade facade;

    //ex: /leiloes?status=ATIVO&page=0&size=5
    @GetMapping("")
    public Page<LeilaoResponse> listarLeiloes( @QuerydslPredicate(root = Leilao.class) Predicate predicate,
            @PageableDefault(value = 10)
            @SortDefault(sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable) {
    	return facade.listarLeiloes(predicate, pageable).map(leilao -> new LeilaoResponse(leilao));
    }

	@GetMapping("/participante/{id}")
    public Page<LeilaoResponse> listarLeiloesPorParticipanteId(@PathVariable("id") Long participanteId,
			@PageableDefault(value = 10)
			@SortDefault(sort = "id", direction = Sort.Direction.ASC)
			Pageable pageable) {
				System.out.println("Listando leilões para participante ID: " + participanteId);
    	return facade.listarLeiloesPorParticipanteId(participanteId, pageable).map(leilao -> new LeilaoResponse(leilao));
    }
    
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

	@PatchMapping("{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public LeilaoResponse atualizarLeilao(@PathVariable("id") Long id, @RequestBody @Valid UpdateLeilaoRequest obj) {
		Leilao l = facade.atualizarLeilao(id, obj);
		return new LeilaoResponse(l);
	}
	
	@DeleteMapping("/{id}")
	public void cancelarLeilao(@PathVariable("id") Long id, @RequestBody @Valid CancelarLeilaoRequest obj) {
		facade.cancelarLeilao(obj.getUserId(), id);
	}
}
