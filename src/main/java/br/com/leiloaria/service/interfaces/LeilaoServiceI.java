package br.com.leiloaria.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;

import br.com.leiloaria.controller.dto.leilao.LeilaoRequest;
import br.com.leiloaria.controller.dto.leilao.UpdateLeilaoRequest;
import br.com.leiloaria.model.Leilao;
import br.com.leiloaria.model.Usuario;
import br.com.leiloaria.model.enums.StatusLeilao;

public interface LeilaoServiceI {

	Page<Leilao> listar(Predicate filtro, Pageable pageable);

	Leilao buscarPorId(Long id);
	
	void excluir(Long id); 
	
	List<Leilao> buscarLeiloesPorUsuario(Long usuarioId);
	
	Leilao cadastrar(LeilaoRequest lReq, Usuario u);
	
	Leilao atualizarLeilao(Long id, UpdateLeilaoRequest lReq);
	
	void atualizarStatusLeilao(Long leilaoId, StatusLeilao novoStatus); 
}