package br.com.leiloaria.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;

import br.com.leiloaria.controller.dto.items.ItemRequest;
import br.com.leiloaria.controller.dto.items.UpdateItemRequest;
import br.com.leiloaria.controller.dto.leilao.LeilaoRequest;
import br.com.leiloaria.controller.dto.leilao.UpdateLeilaoRequest;
import br.com.leiloaria.model.Categoria;
import br.com.leiloaria.model.Item;
import br.com.leiloaria.model.Lance;
import br.com.leiloaria.model.Leilao;
import br.com.leiloaria.model.Lote;
import br.com.leiloaria.model.Usuario;
import br.com.leiloaria.model.enums.StatusLeilao;
import br.com.leiloaria.repository.CategoriaRepository;
import br.com.leiloaria.repository.ItemRepository;
import br.com.leiloaria.repository.LeilaoRepository;
import br.com.leiloaria.repository.LoteRepository;
import br.com.leiloaria.repository.UsuarioRepository;
import br.com.leiloaria.service.exceptions.AtualizarLeilaoInvalidoException;
import br.com.leiloaria.service.exceptions.ExcluirLeilaoJaIniciadoException;
import br.com.leiloaria.service.exceptions.RecursoNaoEncontradoException;
import br.com.leiloaria.service.interfaces.LeilaoServiceI;

@Service
public class LeilaoService implements LeilaoServiceI {
    @Autowired
    private LeilaoRepository repository;
    
    @Autowired
    private LoteRepository loteRepo;
    
    @Autowired
    private CategoriaRepository categoriaRepo;
    
    @Autowired
    private ItemRepository itemRepo;
    
    @Autowired
    private UsuarioRepository usuarioRepo;
    
    @Override
    @Transactional(readOnly = true)
    public Page<Leilao> listar(Predicate filtro, Pageable pageable) {
        return repository.findAll(filtro, pageable);
    }
	
    @Override
    @Transactional(readOnly = true)
	public Leilao buscarPorId(Long id) {
        Leilao leilao = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Leilao não encontrado"));
            return leilao;
	}
    
    @Override
    @Transactional(readOnly = true)
	public List<Leilao> buscarLeiloesPorUsuario(Long usuarioId) {
        usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuario não encontrado"));
        
        List<Leilao> leiloes = repository.findByProprietarioId(usuarioId);
        
        return leiloes;
	}
    
    @Override
    public Leilao cadastrar(LeilaoRequest lReq, Usuario u) {
    	Leilao novoLeilao = new Leilao();
    	novoLeilao.setInicio(lReq.getInicio());
    	novoLeilao.setFim(lReq.getFim());
    	novoLeilao.setPrazoPagamento(lReq.getPrazoPagamento());
    	novoLeilao.setStatus(StatusLeilao.PENDENTE);
    	
    	Lote loteLeilao = new Lote();
    	loteLeilao.setNome(lReq.getNome());
    	loteLeilao.setDescricao(lReq.getDescricao());
    	loteLeilao.setLanceMinimo(lReq.getLanceMinimo());
    	
    	List<Item> itens = new ArrayList<Item>();
    	for (ItemRequest itemRequest : lReq.getItens()) {
			Item itemTemp = new Item();
			itemTemp.setNome(itemRequest.getNome());
			itemTemp.setDescricao(itemRequest.getDescricao());
			itemTemp.setCondicao(itemRequest.getCondicao());
			itemTemp.setImagens(itemRequest.getImagens());
			
			List<Categoria> itemCategorias = new ArrayList<Categoria>();
			
			//pode ser otimizado para Batch, mas para fins didáticos vai por n+1
			for (Long categoriaId : itemRequest.getCategoriasId()) {
				Categoria categoria = categoriaRepo.findById(categoriaId)
			      .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));
				itemCategorias.add(categoria);
			}
			
			itemTemp.setCategorias(itemCategorias);
			
			itens.add(itemTemp);
		}
    	
    	loteLeilao.setItens(itens);
    	novoLeilao.setLote(loteLeilao);
    	novoLeilao.setProprietario(u);
    	
    	return repository.save(novoLeilao);
    }
    
    @Override
    public Leilao atualizarLeilao(Long id, UpdateLeilaoRequest lReq) {
        Leilao leilao = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Leilao não encontrado"));
        
        if(leilao.getStatus() != StatusLeilao.PENDENTE) {
        	throw new AtualizarLeilaoInvalidoException("Status já não está mais pendente e não pode ser mais atualizado");
        }
        
        if(lReq.getInicio() != null) {
        	leilao.setInicio(lReq.getInicio());
        }
        
        if(lReq.getFim() != null) {
        	leilao.setFim(lReq.getFim());
        }
        
        if(lReq.getPrazoPagamento() != null) {
        	leilao.setPrazoPagamento(lReq.getPrazoPagamento());
        }
        
    	Lote loteLeilao = leilao.getLote();
    	
        if(lReq.getNome() != null) {
        	loteLeilao.setNome(lReq.getNome());
        }
        
        if(lReq.getDescricao() != null) {
        	loteLeilao.setDescricao(lReq.getDescricao());
        }
        
        if(lReq.getLanceMinimo() != null) {
        	loteLeilao.setLanceMinimo(lReq.getLanceMinimo());
        }
        
        if(lReq.getItens() != null && lReq.getItens().size() > 0) {
        	for (UpdateItemRequest itemReq : lReq.getItens()) {
                Item item = itemRepo.findById(itemReq.getIdItem())
                        .orElseThrow(() -> new RecursoNaoEncontradoException("Item não encontrado"));
                
                if(itemReq.getNome() != null) {
                	item.setNome(itemReq.getNome());
                }
                
                if(itemReq.getDescricao() != null) {
                	item.setDescricao(itemReq.getDescricao());
                }
                
                if(itemReq.getImagens() != null) {
                	item.setImagens(itemReq.getImagens());
                }
                
                if(itemReq.getCondicao() != null) {
                	item.setCondicao(itemReq.getCondicao());
                }
                
    			if(itemReq.getCategoriasId() != null && itemReq.getCategoriasId().size() > 0) {
    				List<Categoria> itemCategorias = new ArrayList<Categoria>();
    				
    				//pode ser otimizado para Batch, mas para fins didáticos vai por n+1
    				for (Long categoriaId : itemReq.getCategoriasId()) {
    					Categoria categoria = categoriaRepo.findById(categoriaId)
    				      .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));
    					itemCategorias.add(categoria);
    				}
    				
    				item.setCategorias(itemCategorias);
    			}
			}
        }
        
        return repository.save(leilao);
    }
    
    @Override
    public void atualizarStatusLeilao(Long leilaoId, StatusLeilao novoStatus) {
        Leilao leilao = repository.findById(leilaoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Leilao não encontrado"));
        
        if(leilao.getStatus() == StatusLeilao.ABERTO && novoStatus == StatusLeilao.PENDENTE ) {
        	throw new AtualizarLeilaoInvalidoException("Leilão está aberto e não pode ser marcado como pendente mais");
        }
        
        if(leilao.getStatus() == StatusLeilao.PENDENTE && novoStatus == StatusLeilao.FINALIZADO ) {
        	throw new AtualizarLeilaoInvalidoException("Leilão está pendente e deve ser aberto antes de ser finalizado");
        }
        
        if((leilao.getStatus() == StatusLeilao.FINALIZADO || leilao.getStatus() == StatusLeilao.CANCELADO) && novoStatus != null ) {
        	throw new AtualizarLeilaoInvalidoException("Leilão está finalizado e não pode ser atualizado mais");
        }
        
        leilao.setStatus(novoStatus);
        repository.save(leilao);
    }
    
    
    @Override
    public void excluir(Long id) {
        Leilao leilao = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Leilao não encontrado"));
        
        if(leilao.getStatus() != StatusLeilao.PENDENTE) {
        	throw new ExcluirLeilaoJaIniciadoException("Leilão já foi iniciado");
        }
    	
        repository.deleteById(id);
    }
}
