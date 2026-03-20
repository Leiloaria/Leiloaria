package br.com.leiloaria.facade;

import br.com.leiloaria.service.ItemService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import br.com.leiloaria.controller.dto.auth.LoginRequest;
import br.com.leiloaria.controller.dto.auth.LoginResponse;
import br.com.leiloaria.controller.dto.auth.RegisterRequest;
import br.com.leiloaria.controller.dto.auth.RegisterResponse;
import br.com.leiloaria.controller.dto.avaliacao.AvaliacaoRequest;
import br.com.leiloaria.controller.dto.items.UpdateItemRequest;
import br.com.leiloaria.controller.dto.user.UserRequest;
import br.com.leiloaria.model.Avaliacao;
import br.com.leiloaria.controller.dto.lance.LanceRequest;
import br.com.leiloaria.controller.dto.leilao.LeilaoRequest;
import br.com.leiloaria.controller.dto.leilao.UpdateLeilaoRequest;
import br.com.leiloaria.controller.dto.venda.UpdateVendaRequest;
import br.com.leiloaria.model.Categoria;
import br.com.leiloaria.model.Item;
import br.com.leiloaria.model.Lance;
import br.com.leiloaria.model.Leilao;
import br.com.leiloaria.model.Lote;
import br.com.leiloaria.model.Pagamento;
import br.com.leiloaria.model.Usuario;
import br.com.leiloaria.service.AuthorizationService;
import br.com.leiloaria.model.Venda;
import br.com.leiloaria.model.enums.StatusLeilao;
import br.com.leiloaria.model.enums.StatusPagamento;
import br.com.leiloaria.service.UsuarioService;
import br.com.leiloaria.service.exceptions.AtualizarLanceInvalidoException;
import br.com.leiloaria.service.exceptions.AtualizarLeilaoInvalidoException;
import br.com.leiloaria.service.exceptions.AtualizarPagamentoVendaException;
import br.com.leiloaria.service.exceptions.RecursoNaoEncontradoException;
import br.com.leiloaria.service.interfaces.AuthServiceI;
import br.com.leiloaria.service.interfaces.AvaliacaoServiceI;
import br.com.leiloaria.service.interfaces.CategoriaServiceI;
import br.com.leiloaria.service.interfaces.LanceServiceI;
import br.com.leiloaria.service.interfaces.LeilaoServiceI;
import br.com.leiloaria.service.interfaces.LoteServiceI;
import br.com.leiloaria.service.interfaces.PagamentoServiceI;
import br.com.leiloaria.service.interfaces.UsuarioServiceI;
import br.com.leiloaria.service.interfaces.VendaServiceI;

@Service
public class Facade {

    private final ItemService itemService;

    private final UsuarioService usuarioService;

    @Autowired
    private AuthServiceI authService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private CategoriaServiceI categoriaService;

    @Autowired
    private UsuarioServiceI userService;

    @Autowired
    private AvaliacaoServiceI avaliacaoService;

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
    
    @Value("${security.payment-gateway-token}")
    private String paymentGatewayToken;


    Facade(UsuarioService usuarioService, ItemService itemService) {
        this.usuarioService = usuarioService;
        this.itemService = itemService;
    }


    // CATEGORIAS
    public Page<Categoria> listarCategorias(Predicate filtro, Pageable pageable) {
        return categoriaService.listar(filtro, pageable);
    }

    public Categoria cadastrarCategoria(Long usuarioId, Categoria obj) {
    	Usuario usuario = usuarioService.buscarPorId(usuarioId);
    	
    	if(!usuario.getEhAdmin()) {
    		throw new AtualizarLeilaoInvalidoException("Categoria não pode ser cadastrada por esse usuário");
    	}
    	
        return categoriaService.cadastrar(obj);
    }

    public Categoria atualizarCategoria(Long usuarioId, Long id, Categoria obj) {
    	Usuario usuario = usuarioService.buscarPorId(usuarioId);
    	
    	if(!usuario.getEhAdmin()) {
    		throw new AtualizarLeilaoInvalidoException("Categoria não pode ser atualizada por esse usuário");
    	}
    	
        return categoriaService.atualizar(id, obj);
    }

    public void excluirCategoria(Long usuarioId, Long id) {
    	Usuario usuario = usuarioService.buscarPorId(usuarioId);
    	
    	if(!usuario.getEhAdmin()) {
    		throw new AtualizarLeilaoInvalidoException("Categoria não pode ser excluída por esse usuário");
    	}
    	
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

    public Usuario buscarUsuarioPorEmail(String email) {
        return userService.buscarPorEmail(email);
    }

    public Page<Usuario> listarTodosUsuarios(Predicate predicate, Pageable pageable) {
        // verificar se ta logado ou é admin
        return userService.listar(predicate, pageable);
    }

    public Usuario atualizarUsuario(Long id, UserRequest userUpdateRequest) {
        // verificar
        Usuario usuario = userUpdateRequest.convertToEntity(userUpdateRequest, modelMapper);
        Usuario usuarioAtualizado = userService.atualizar(id, usuario);

        return usuarioAtualizado;
    }

    public void deletarUsuario(Long id) {
        userService.excluir(id);
    }

    // AVALIAÇÃO

    public Avaliacao buscarAvaliacaoPorId(Long id) {
        //qualquer user pode ver uma avaliacao.
        Avaliacao avaliacao = avaliacaoService.buscarPorId(id);
        return avaliacao;
    }

    public Avaliacao atualizarAvaliacao(Long id, AvaliacaoRequest avaliacaoRequest) {
        Avaliacao avaliacaoExistente = avaliacaoService.buscarPorId(id);

        if (!authorizationService.isUsuarioAutorizadoParaAvaliacao(avaliacaoExistente)) {
            throw new AccessDeniedException(
                    "Apenas o administrador ou o usuário que fez a avaliação pode atualizá-la.");
        }

        Avaliacao avaliacao = avaliacaoRequest.convertToEntity(avaliacaoRequest, modelMapper);
        return avaliacaoService.atualizar(id, avaliacao);
    }

    public void deletarAvaliacao(Long id) {
        Avaliacao avaliacaoExistente = avaliacaoService.buscarPorId(id);
        if (!authorizationService.isUsuarioAutorizadoParaAvaliacao(avaliacaoExistente)) {
            throw new AccessDeniedException(
                    "Apenas o administrador ou o usuário que fez a avaliação pode atualizá-la.");
        }

        avaliacaoService.excluir(id);
    }

    public Page<Avaliacao> listarAvaliacoesPorLote(Long loteId, Predicate predicate, Pageable pageable) {
        // admin ou qualquer user
        return avaliacaoService.listarPorLote(loteId, predicate, pageable);
    }

    public Page<Avaliacao> listarTodasAvaliacoes(Predicate predicate, Pageable pageable) {
        // somente admin
        return avaliacaoService.listar(predicate, pageable);
    }

    public Avaliacao avaliarCompra(Long loteId, AvaliacaoRequest avaliacaoRequest) {
        Usuario logado = authorizationService.getUsuarioLogado();
        // Verificar se o lote existe
        Lote lote = loteService.buscarLoteById(loteId);
        // Verificar se o leilao já finalizou (pq ai ta aberto a avaliacao)
        if(lote.getLeilao().estaAberto()) {
        	throw new RecursoNaoEncontradoException("Leilão ainda está aberto. Não é possível avaliar a compra.");
        }
        // Verificar se o usuario ja avaliou o lote(dentro do service já faz) - ok
        // verificar se o usuario da sessao é o mesmo que comprou (maior lance da lista de lances).
        if (!authorizationService.isUsuarioVencedorDoLote(loteId)) {
            throw new AccessDeniedException("Apenas o usuário que comprou o lote pode avaliá-lo.");
        }
        
        Avaliacao avaliacao = avaliacaoRequest.convertToEntity(avaliacaoRequest, modelMapper);
        avaliacao.setUsuario(logado);
        avaliacao.setLote(lote);
        return avaliacaoService.avaliarCompra(avaliacao);
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
    
    //list by user
    public List<Lance> buscarLancesPorUsuario(Long usuarioId) {
        return lanceService.buscarLancesPorUsuario(usuarioId);
    }
    
    //create
    public Lance criarLance(LanceRequest lanceRequest) {
        Lote lanceLote = loteService.buscarLoteById(lanceRequest.getLoteId());
        
        Leilao leilaoLote = lanceLote.getLeilao();
        
        if(!leilaoLote.estaAberto()) {
        	throw new AtualizarLanceInvalidoException("Leilão não está aberto");
        }
        
        if(leilaoLote.getProprietario().getId() == lanceRequest.getUsuarioId()) {
        	throw new AtualizarLanceInvalidoException("Não é possível dar lance sendo o proprietário do leilão");
        }
        
        if(lanceLote.getLanceMinimo().compareTo(lanceRequest.getValor()) >= 0) {
        	throw new AtualizarLanceInvalidoException("Novo lance não é maior que o lance mínimo desse leilão");
        }
        
        Lance maiorLance = lanceService.buscarMaiorLance(lanceLote.getId());
        
        if(maiorLance != null && maiorLance.getValor().compareTo(lanceRequest.getValor()) >= 0) {
        	throw new AtualizarLanceInvalidoException("Novo lance não é maior que o maior lance para esse leilão");
        }
        
        Usuario usuarioLance = userService.buscarPorId(lanceRequest.getUsuarioId());
        
        Lance lance = new Lance();
        lance.setLote(lanceLote);
        lance.setUsuario(usuarioLance);
        lance.setValor(lanceRequest.getValor());
        lance.setTimestamp(LocalDateTime.now());
    	
    	return lanceService.cadastrar(lance);
    }
    
    //update
    public Lance atualizarValorLance(Long id, BigDecimal novoValor) {
        Lance lance = lanceService.buscarPorId(id);
        
        Lote lanceLote = lance.getLote();
        
        Leilao leilaoLote = lanceLote.getLeilao();
        
        if(!leilaoLote.estaAberto()) {
        	throw new AtualizarLanceInvalidoException("Leilão não está aberto");
        }
        
        Lance maiorLanceLote = lanceService.buscarMaiorLance(lanceLote.getId());
        
        if(maiorLanceLote == null) {
        	throw new RecursoNaoEncontradoException("Maior Lance não encontrado");
        }
        
        if(maiorLanceLote.getValor().compareTo(novoValor) >= 0) {
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
    
    //list
    public Page<Venda> listarVenda(Predicate filtro, Pageable pageable) {
        return vendaService.listar(filtro, pageable);
    }

    //findById
    public Venda buscarVendaPorId(Long id) {
        return vendaService.buscarPorId(id);
    }
    
    //update
    public Venda atualizarPagamentoVenda(Long vendaId, UpdateVendaRequest vendaPagamento) {
    	Venda venda = vendaService.buscarPorId(vendaId);
    	
    	if(venda.getLance().getLote().getLeilao().getStatus() == StatusLeilao.CANCELADO) {
    		throw new AtualizarPagamentoVendaException("Leilão foi cancelado por ter passado do prazo de pagamento");
    	}
    	
    	Pagamento pagamento = pagamentoService.gerarFormaPagamento(vendaPagamento);
    	
    	return vendaService.adicionarPagamentoVenda(venda, pagamento);
    }
    
    //update
    public Venda atualizarStatusPagamentoVenda(String authorizationToken, Long vendaId, StatusPagamento novoStatus) {
    	if(!authorizationToken.equals(paymentGatewayToken)) {
    		throw new AtualizarPagamentoVendaException("Token de gateway de pagamento inválido");
    	}
    	
    	Venda venda = vendaService.buscarPorId(vendaId);
    	
    	Leilao leilaoVenda = venda.getLance().getLote().getLeilao();
    	
    	if(leilaoVenda.getStatus() == StatusLeilao.CANCELADO) {
    		throw new AtualizarPagamentoVendaException("Leilão foi cancelado por ter passado do prazo de pagamento");
    	}
    	
    	Venda vendaFinalizada = vendaService.atualizarStatusPagamentoVenda(vendaId, novoStatus);
    	
    	if(novoStatus == StatusPagamento.PAID) {
    		leilaoService.atualizarStatusLeilao(leilaoVenda.getId(), StatusLeilao.FINALIZADO);
    	}
    	
    	return vendaFinalizada;
    }
    
    
    //LEILAO
    
    //list
    public Page<Leilao> listarLeiloes(Predicate filtro, Pageable pageable) {
        return leilaoService.listar(filtro, pageable);
    }

    public Page<Leilao> listarLeiloesPorParticipanteId(Long participanteId, Pageable pageable) {
        return leilaoService.listarLeiloesPorParticipanteId(participanteId, pageable);
    }
    
    //list for scheduler
    public List<Leilao> listarLeiloesParaAbrir(){
    	return leilaoService.listarLeiloesParaAbrir();
    }
    
    public List<Leilao> listarLeiloesParaFinalizar(){
    	return leilaoService.listarLeiloesParaFinalizar();
    }
    
    public List<Leilao> listarLeiloesParaCancelar(){
    	return leilaoService.listarLeiloesParaCancelar();
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
    	Leilao leilao = buscarLeilaoPorId(leilaoId);
        Leilao obj = lReq.toModel();
        
        Lote lote = leilao.getLote();
        Lote loteObj = obj.getLote();

        List<Item> itens = new ArrayList<>();

        if (lReq.getItens() != null && lReq.getItens().size() > 0) {
            for (UpdateItemRequest itemReq : lReq.getItens()) {
                Item item;
                Item itemObj = itemReq.toModel();
                itemObj.getCategorias().forEach((x) -> buscarCategoriaPorId(x.getId()));
                try {
                    item = buscarItemPorId(itemReq.getIdItem());
                    item = atualizarItem(item.getId(), itemObj);
                } catch (Exception e) {
                    item = cadastrarItem(itemObj);
                }
                itens.add(item);
            }
        }

        loteObj.setItens(itens);
        leilao.setLote(
            atualizarLote(lote.getId(), loteObj)
        );
        
        return leilaoService.atualizarLeilao(leilaoId, obj);
    }
    
    //update status
    public void atualizarStatusLeilao(Long leilaoId, StatusLeilao novoStatus) {
    	leilaoService.atualizarStatusLeilao(leilaoId, novoStatus);
    }
    
    //delete
    public void cancelarLeilao(Long usuarioId, Long leilaoId) {
    	Usuario usuario = usuarioService.buscarPorId(usuarioId);
    	Leilao leilao = leilaoService.buscarPorId(leilaoId);
    	
    	if(usuario.getId() != leilao.getProprietario().getId() || !usuario.getEhAdmin()) {
    		throw new AtualizarLeilaoInvalidoException("Leilão não pode ser cancelado por esse usuário");
    	}
    	
    	leilaoService.atualizarStatusLeilao(leilaoId, StatusLeilao.CANCELADO);
    }
    
    public void encerrarLeilao(Long leilaoId) {
    	Leilao leilao = leilaoService.buscarPorId(leilaoId);
    	
    	leilaoService.atualizarStatusLeilao(leilaoId, StatusLeilao.AGUARDANDO_PAGAMENTO);
    	
    	Lance maiorLance = lanceService.buscarMaiorLance(leilao.getLote().getId());
    	
    	if(maiorLance == null) {
    		leilaoService.atualizarStatusLeilao(leilaoId, StatusLeilao.CANCELADO);
    		return;
    	}
    	
    	vendaService.gerarVenda(maiorLance);
    }

    //ITEM
    public Item cadastrarItem(Item obj) {
        return itemService.cadastrar(obj);
    }

    public Item buscarItemPorId(Long id) {
        return itemService.buscarPorId(id);
    }

    public Item atualizarItem(Long id, Item obj) {
        return itemService.atualizar(id, obj);
    }

    //LOTE
    public Lote buscarLotePorId(Long id) {
        return loteService.buscarLoteById(id);
    }

    //ITEM
    public Lote atualizarLote(Long id, Lote obj) {
        return loteService.atualizarLote(id, obj);
    }
}
