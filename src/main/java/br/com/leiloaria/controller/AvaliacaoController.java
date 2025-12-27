package br.com.leiloaria.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import br.com.leiloaria.controller.dto.avaliacao.AvaliacaoRequest;
import br.com.leiloaria.controller.dto.avaliacao.AvaliacaoResponse;
import br.com.leiloaria.facade.Facade;
import br.com.leiloaria.model.Avaliacao;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController {

    @Autowired
    private Facade facade;

    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/lote/{loteId}")
    public ResponseEntity<?> avaliarCompra(@PathVariable Long loteId, @RequestBody AvaliacaoRequest avaliacaoRequest) {
        Avaliacao avaliacao = facade.avaliarCompra(loteId, avaliacaoRequest);
        return new ResponseEntity<>(new AvaliacaoResponse(avaliacao, modelMapper), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Avaliacao avaliacao = facade.buscarAvaliacaoPorId(id);
        return new ResponseEntity<>(new AvaliacaoResponse(avaliacao, modelMapper), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public Page<AvaliacaoResponse> listarTodos(
            @QuerydslPredicate(root = Avaliacao.class) Predicate predicate,
            @PageableDefault(value = 2) @SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        return facade.listarTodasAvaliacoes(predicate, pageable)
                .map(avaliacao -> new AvaliacaoResponse(avaliacao, modelMapper));

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/lote/{loteId}")
    public Page<AvaliacaoResponse> listarTodosByLote(
            @PathVariable Long loteId,
            @QuerydslPredicate(root = Avaliacao.class) Predicate predicate,
            @PageableDefault(value = 10) @SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        return facade.listarAvaliacoesPorLote(loteId, predicate, pageable)
                .map(avaliacao -> new AvaliacaoResponse(avaliacao, modelMapper));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody AvaliacaoRequest avaliacaoRequest) {
        Avaliacao avaliacaoAtualizada = facade.atualizarAvaliacao(id, avaliacaoRequest);
        return new ResponseEntity<>(new AvaliacaoResponse(avaliacaoAtualizada, modelMapper), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')") // TODO: o proprio user pode apagar seu perfil
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        facade.deletarAvaliacao(id);
        return ResponseEntity.noContent().build();
    }

}
