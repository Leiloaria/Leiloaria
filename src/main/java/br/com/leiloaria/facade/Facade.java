package br.com.leiloaria.facade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import br.com.leiloaria.controller.dto.auth.LoginRequest;
import br.com.leiloaria.controller.dto.auth.LoginResponse;
import br.com.leiloaria.controller.dto.auth.RegisterRequest;
import br.com.leiloaria.controller.dto.auth.RegisterResponse;
import br.com.leiloaria.controller.dto.items.ItemRequest;
import br.com.leiloaria.controller.dto.lance.LanceRequest;
import br.com.leiloaria.controller.dto.leilao.LeilaoRequest;
import br.com.leiloaria.controller.dto.leilao.UpdateLeilaoRequest;
import br.com.leiloaria.controller.dto.user.UserRequest;
import br.com.leiloaria.controller.dto.venda.VendaRequest;
import br.com.leiloaria.model.Categoria;
import br.com.leiloaria.model.Item;
import br.com.leiloaria.model.Lance;
import br.com.leiloaria.model.Leilao;
import br.com.leiloaria.model.Lote;
import br.com.leiloaria.model.Pagamento;
import br.com.leiloaria.model.Usuario;
import br.com.leiloaria.model.Venda;
import br.com.leiloaria.model.enums.FormaPagamento;
import br.com.leiloaria.model.enums.StatusLeilao;
import br.com.leiloaria.model.enums.StatusPagamento;
import br.com.leiloaria.service.UsuarioService;
import br.com.leiloaria.service.exceptions.AtualizarLanceInvalidoException;
import br.com.leiloaria.service.exceptions.RecursoNaoEncontradoException;
import br.com.leiloaria.service.interfaces.AuthServiceI;
import br.com.leiloaria.service.interfaces.CategoriaServiceI;
import br.com.leiloaria.service.interfaces.LanceServiceI;
import br.com.leiloaria.service.interfaces.LeilaoServiceI;
import br.com.leiloaria.service.interfaces.LoteServiceI;
import br.com.leiloaria.service.interfaces.PagamentoServiceI;
import br.com.leiloaria.service.interfaces.UsuarioServiceI;
import br.com.leiloaria.service.interfaces.VendaServiceI;

@Service
public class Facade {

    @Autowired
    private AuthServiceI authService;

    @Autowired
    private CategoriaServiceI categoriaService;
    
    @Autowired
    private UsuarioServiceI userService;
    
    @Autowired
    private LanceServiceI lanceService;
    
    @Autowired
    private LoteServiceI loteService;
    
    @Autowired
    private VendaServiceI vendaService;
    
    @Autowired
    private PagamentoServiceI pagamentoService;
    
    @Autowired
    private LeilaoServiceI leilaoService;
    
    @Autowired
    private ModelMapper modelMapper;

    // CATEGORIAS
    public Page<Categoria> listarCategorias(Predicate filtro, Pageable pageable) {
        return categoriaService.listar(filtro, pageable);
    }

    public Categoria cadastrarCategoria(Categoria obj) {
        return categoriaService.cadastrar(obj);
    }

    public Categoria atualizarCategoria(Long id, Categoria obj) {
        return categoriaService.atualizar(id, obj);
    }

    public void excluirCategoria(Long id) {
        categoriaService.excluir(id);
    }

    public Categoria buscarCategoriaPorId(Long id) {
        return categoriaService.buscarPorId(id);
    }

    public Categoria addSubCategoria(Long id, Categoria obj) {
        return categoriaService.addSubCategoria(id, obj);
    }

    // AUTENTICAÇÃO
    public LoginResponse login(LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    public RegisterResponse register(RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }
    
    // USER
    
	public Usuario buscarUsuarioPorId(Long id) {
		Usuario usuario = userService.buscarPorId(id);
		return usuario;
	}

    public Page<Usuario> listarTodosUsuarios(Predicate predicate, Pageable pageable) {
 
        return userService.listar(predicate, pageable);
    }

    public Usuario atualizarUsuario(Long id, UserRequest userUpdateRequest) {
    	Usuario usuario = userUpdateRequest.convertToEntity(userUpdateRequest, modelMapper);
    	System.out.println(usuario.toString());
    	Usuario usuarioAtualizado = userService.atualizar(id, usuario);
       
        return usuarioAtualizado;
    }

    public void deletarUsuario(Long id) {
        userService.excluir(id);
    }
    
    //LANCE
    
    //list
    public Page<Lance> listarLances(Predicate filtro, Pageable pageable) {
        return lanceService.listar(filtro, pageable);
    }
    
    //list by lote
    public List<Lance> listarLancesPorLote(Long loteId){
    	return lanceService.buscarLancesPorLoteId(loteId);
    }

    //findById
    public Lance buscarLancePorId(Long id) {
        return lanceService.buscarPorId(id);
    }
    
    //create
    public Lance criarLance(LanceRequest lanceRequest) {
        Lote lanceLote = loteService.buscarLoteById(lanceRequest.getLoteId());
        
        Leilao leilaoLote = lanceLote.getLeilao();
        
        if(!leilaoLote.estaAberto()) {
        	throw new AtualizarLanceInvalidoException("Leilão não está aberto");
        }
        
        Lance maiorLance = lanceService.buscarMaiorLance(lanceLote.getId());
        
        if(maiorLance.getValor().compareTo(lanceRequest.getValor()) > 0) {
        	throw new AtualizarLanceInvalidoException("Novo lance não é maior que o maior lance para esse leilão");
        }
        
        Usuario usuarioLance = userService.buscarPorId(lanceRequest.getUsuarioId());
        
        Lance lance = new Lance();
        lance.setLote(lanceLote);
        lance.setUsuario(usuarioLance);
    	
    	return lanceService.cadastrar(lance);
    }
    
    //update
    public Lance atualizarValor(Long id, BigDecimal novoValor) {
        Lance lance = lanceService.buscarPorId(id);
        
        Lote lanceLote = lance.getLote();
        
        Leilao leilaoLote = lanceLote.getLeilao();
        
        if(!leilaoLote.estaAberto()) {
        	throw new AtualizarLanceInvalidoException("Leilão não está aberto");
        }
        
        Lance maiorLote = lanceService.buscarMaiorLance(lanceLote.getId());
        
        if(maiorLote.getValor().compareTo(novoValor) > 0) {
        	throw new AtualizarLanceInvalidoException("Novo lance não é maior que o maior lance para esse leilão");
        }
    	
    	return lanceService.atualizarValor(id, novoValor);
    }
    
    //remove
    public void excluirLance(Long id) {
        Lance lance = lanceService.buscarPorId(id);
        
        Lote lanceLote = lance.getLote();
        
        Leilao leilaoLote = lanceLote.getLeilao();
        
        if(!leilaoLote.estaAberto()) {
        	throw new AtualizarLanceInvalidoException("Leilão não está aberto");
        }
        
        lanceService.excluir(id);
    }
    
    //VENDA
    
    
    //create
    public Venda gerarVenda(VendaRequest venda) {
    	Lance lance = lanceService.buscarPorId(venda.getLanceId());
    	
    	Pagamento pagamento = pagamentoService.gerarFormaPagamento(venda);
    	
    	return vendaService.gerarVenda(lance, pagamento);
    }
    
    //list
    public Page<Venda> listarVenda(Predicate filtro, Pageable pageable) {
        return vendaService.listar(filtro, pageable);
    }

    //findById
    public Venda buscarVendaPorId(Long id) {
        return vendaService.buscarPorId(id);
    }
    
    //update
    public Venda atualizarStatusPagamentoVenda(Long vendaId, StatusPagamento novoStatus) {
    	return vendaService.atualizarStatusPagamentoVenda(vendaId, novoStatus);
    }
    
    
    //LEILAO
    
    //list
    public Page<Leilao> listarLeiloes(Predicate filtro, Pageable pageable) {
        return leilaoService.listar(filtro, pageable);
    }
    
    //list
    public List<Leilao> buscarLeiloesPorUsuario(Long usuarioId) {
        return leilaoService.buscarLeiloesPorUsuario(usuarioId);
    }
    
    //findById
    public Leilao buscarLeilaoPorId(Long id) {
        return leilaoService.buscarPorId(id);
    }
    
    //create
    public Leilao cadastrarLeilao(LeilaoRequest lReq, Long usuarioId) {
    	Usuario usuario = userService.buscarPorId(usuarioId);
    	
    	return leilaoService.cadastrar(lReq, usuario);
    }
    
    //update
    public Leilao atualizarLeilao(Long leilaoId, UpdateLeilaoRequest lReq) {
    	return leilaoService.atualizarLeilao(leilaoId, lReq);
    }
    
    //update status
    public void atualizarStatusLeilao(Long leilaoId, StatusLeilao novoStatus) {
    	leilaoService.atualizarStatusLeilao(leilaoId, novoStatus);
    }
    
    //delete
    public void excluirLeilao(Long leilaoId) {
    	leilaoService.excluir(leilaoId);
    }
}
