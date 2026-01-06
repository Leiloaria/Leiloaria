package br.com.leiloaria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.leiloaria.controller.dto.items.ItemRequest;
import br.com.leiloaria.controller.dto.leilao.LeilaoRequest;
import br.com.leiloaria.controller.dto.leilao.UpdateLeilaoRequest;
import br.com.leiloaria.model.Categoria;
import br.com.leiloaria.model.Item;
import br.com.leiloaria.model.Leilao;
import br.com.leiloaria.model.Lote;
import br.com.leiloaria.model.Usuario;
import br.com.leiloaria.model.enums.Condicao;
import br.com.leiloaria.model.enums.StatusLeilao;
import br.com.leiloaria.repository.CategoriaRepository;
import br.com.leiloaria.repository.ItemRepository;
import br.com.leiloaria.repository.LeilaoRepository;
import br.com.leiloaria.repository.LoteRepository;
import br.com.leiloaria.repository.UsuarioRepository;
import br.com.leiloaria.service.exceptions.AtualizarLeilaoInvalidoException;
import br.com.leiloaria.service.exceptions.ExcluirLeilaoJaIniciadoException;
import br.com.leiloaria.service.exceptions.RecursoNaoEncontradoException;

@ExtendWith(MockitoExtension.class)
@DisplayName("LeilaoService Tests")
class LeilaoServiceTest {

    @InjectMocks
    private LeilaoService service;

    @Mock
    private LeilaoRepository repository;

    @Mock
    private LoteRepository loteRepo;

    @Mock
    private CategoriaRepository categoriaRepo;

    @Mock
    private ItemRepository itemRepo;

    @Mock
    private UsuarioRepository usuarioRepo;

    private Leilao leilao;

    @BeforeEach
    void setup() {
        leilao = new Leilao();
        leilao.setId(1L);
        leilao.setStatus(StatusLeilao.PENDENTE);
        leilao.setLote(new Lote());
    }
    
    @Test
    void deveCriarLeilaoComLoteEItens() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João");

        Categoria categoria = new Categoria();
        categoria.setId(10L);
        categoria.setNome("Eletrônicos");

        when(categoriaRepo.findById(10L))
                .thenReturn(Optional.of(categoria));

        when(repository.save(any(Leilao.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ItemRequest itemReq = new ItemRequest();
        itemReq.setNome("Notebook");
        itemReq.setDescricao("Notebook Gamer");
        itemReq.setCondicao(Condicao.NOVO);
        itemReq.setImagens(List.of("img1.png"));
        itemReq.setCategoriasId(List.of(10L));

        LeilaoRequest leilaoReq = new LeilaoRequest();
        leilaoReq.setNome("Lote de Informática");
        leilaoReq.setDescricao("Itens de informática");
        leilaoReq.setLanceMinimo(BigDecimal.valueOf(500));
        leilaoReq.setInicio(LocalDateTime.now());
        leilaoReq.setFim(LocalDateTime.now().plusDays(1));
        leilaoReq.setPrazoPagamento(LocalDateTime.now().plusDays(2));
        leilaoReq.setItens(List.of(itemReq));

        Leilao leilaoCriado = service.cadastrar(leilaoReq, usuario);

        assertNotNull(leilaoCriado);
        assertEquals(StatusLeilao.PENDENTE, leilaoCriado.getStatus());
        assertEquals(usuario, leilaoCriado.getProprietario());

        assertNotNull(leilaoCriado.getLote());
        assertEquals("Lote de Informática", leilaoCriado.getLote().getNome());
        assertEquals(BigDecimal.valueOf(500), leilaoCriado.getLote().getLanceMinimo());

        assertEquals(1, leilaoCriado.getLote().getItens().size());
        Item item = leilaoCriado.getLote().getItens().get(0);

        assertEquals("Notebook", item.getNome());
        assertEquals(1, item.getCategorias().size());
        assertEquals("Eletrônicos", item.getCategorias().get(0).getNome());

        verify(repository).save(any(Leilao.class));
    }
    
    @Test
    void deveBuscarLeilaoPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(leilao));

        Leilao resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void deveLancarExcecaoAoBuscarLeilaoInexistente() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class,
                () -> service.buscarPorId(1L));
    }

    @Test
    void deveAtualizarLeilaoQuandoPendente() {
        UpdateLeilaoRequest req = new UpdateLeilaoRequest();
        req.setNome("Novo nome");

        when(repository.findById(1L)).thenReturn(Optional.of(leilao));
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Leilao atualizado = service.atualizarLeilao(1L, req);

        assertEquals("Novo nome", atualizado.getLote().getNome());
    }

    @Test
    void naoDeveAtualizarLeilaoQuandoNaoPendente() {
        leilao.setStatus(StatusLeilao.ABERTO);
        when(repository.findById(1L)).thenReturn(Optional.of(leilao));

        assertThrows(AtualizarLeilaoInvalidoException.class,
                () -> service.atualizarLeilao(1L, new UpdateLeilaoRequest()));
    }

    @Test
    void deveAtualizarStatusDePendenteParaAberto() {
        when(repository.findById(1L)).thenReturn(Optional.of(leilao));

        service.atualizarStatusLeilao(1L, StatusLeilao.ABERTO);

        assertEquals(StatusLeilao.ABERTO, leilao.getStatus());
        verify(repository).save(leilao);
    }

    @Test
    void naoDeveFinalizarLeilaoPendente() {
        when(repository.findById(1L)).thenReturn(Optional.of(leilao));

        assertThrows(AtualizarLeilaoInvalidoException.class,
                () -> service.atualizarStatusLeilao(1L, StatusLeilao.FINALIZADO));
    }

    @Test
    void deveExcluirLeilaoPendente() {
        when(repository.findById(1L)).thenReturn(Optional.of(leilao));

        service.excluir(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void naoDeveExcluirLeilaoNaoPendente() {
        leilao.setStatus(StatusLeilao.ABERTO);
        when(repository.findById(1L)).thenReturn(Optional.of(leilao));

        assertThrows(ExcluirLeilaoJaIniciadoException.class,
                () -> service.excluir(1L));
    }
}

