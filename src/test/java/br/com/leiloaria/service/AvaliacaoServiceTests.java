package br.com.leiloaria.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Predicate;

import br.com.leiloaria.model.Avaliacao;
import br.com.leiloaria.model.Lote;
import br.com.leiloaria.model.QAvaliacao;
import br.com.leiloaria.model.Usuario;
import br.com.leiloaria.repository.AvaliacaoRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("AvaliacaoService Tests")
public class AvaliacaoServiceTests {

  @Mock
  private AvaliacaoRepository avaliacaoRepository;

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private AvaliacaoService avaliacaoService;

  private Avaliacao avaliacao;
  private Usuario usuario;
  private Lote lote;
  
  @BeforeEach
  void setUp() {
    usuario = new Usuario();
    usuario.setId(1L);
    usuario.setNome("João Silva");

    lote = new Lote();
    lote.setId(1L);
    lote.setNome("Lote Teste");

    avaliacao = new Avaliacao();
    avaliacao.setId(1L);
    avaliacao.setComentario("Ótimo produto");
    avaliacao.setStars(4.3f);
    avaliacao.setUsuario(usuario);
    avaliacao.setLote(lote);
  }

  @Test
  @DisplayName("Deve avaliar compra com sucesso")
  void testAvaliarCompraSuccess() {
    when(avaliacaoRepository.existsByUsuarioIdAndLoteId(1L, 1L)).thenReturn(false);
    when(avaliacaoRepository.save(any(Avaliacao.class))).thenReturn(avaliacao);

    Avaliacao result = avaliacaoService.avaliarCompra(avaliacao);

    assertNotNull(result);
    assertEquals("Ótimo produto", result.getComentario());
    assertEquals(4.3f, result.getStars());

    //para garantir a logica de etapas que devem ser chamadas
    verify(avaliacaoRepository, times(1)).existsByUsuarioIdAndLoteId(1L, 1L);
    verify(avaliacaoRepository, times(1)).save(any(Avaliacao.class));
  }

  @Test
  @DisplayName("Deve lançar exceção ao avaliar compra já avaliada")
  void testAvaliarCompraJaAvaliada() {
    when(avaliacaoRepository.existsByUsuarioIdAndLoteId(1L, 1L)).thenReturn(true);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      avaliacaoService.avaliarCompra(avaliacao);
    });
    assertEquals("Usuário já avaliou este lote.", exception.getMessage());
    verify(avaliacaoRepository, times(1)).existsByUsuarioIdAndLoteId(1L, 1L);
  }

  @Test
  @DisplayName("Deve listar todas as avaliações com paginação")
  void testListar() {
    Pageable pageable = PageRequest.of(0, 10);
    Page<Avaliacao> page = new PageImpl<>(List.of(avaliacao), pageable, 1);

    QAvaliacao qAvaliacao = QAvaliacao.avaliacao;
    Predicate predicate = qAvaliacao.comentario.containsIgnoreCase("ótimo");

    when(avaliacaoRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(page);

    Page<Avaliacao> result = avaliacaoService.listar(predicate, pageable);

    assertNotNull(result);
    assertEquals(1, result.getTotalElements());
    verify(avaliacaoRepository, times(1)).findAll(any(Predicate.class), any(Pageable.class));
  }

  @Test
  @DisplayName("Deve listar avaliações por lote com predicate (comentário contém 'ótimo')")
  void testListarPorLoteComPredicate() {
    Pageable pageable = PageRequest.of(0, 10);
    Page<Avaliacao> page = new PageImpl<>(List.of(avaliacao), pageable, 1);

    QAvaliacao qAvaliacao = QAvaliacao.avaliacao;
    Predicate predicate = qAvaliacao.comentario.containsIgnoreCase("ótimo");

    when(avaliacaoRepository.findAllByLoteIdWithPredicate(eq(1L), eq(predicate), eq(pageable))).thenReturn(page);

    Page<Avaliacao> result = avaliacaoService.listarPorLote(1L, predicate, pageable);

    assertNotNull(result);
    assertEquals(1, result.getTotalElements());
    verify(avaliacaoRepository, times(2)).findAllByLoteIdWithPredicate(eq(1L), eq(predicate), eq(pageable));
  }

  @Test
  @DisplayName("Deve listar avaliações por lote com predicate que não retorna nada")
  void testListarPorLoteComPredicateSemResultados() {
    Pageable pageable = PageRequest.of(0, 10);
    Page<Avaliacao> pageVazia = new PageImpl<>(List.of(), pageable, 0);

    QAvaliacao qAvaliacao = QAvaliacao.avaliacao;
    Predicate predicate = qAvaliacao.comentario.containsIgnoreCase("ótimo");

    when(avaliacaoRepository.findAllByLoteIdWithPredicate(eq(1L), eq(predicate), eq(pageable))).thenReturn(pageVazia);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      avaliacaoService.listarPorLote(1L, predicate, pageable);
    });

    assertEquals("Nenhuma avaliação encontrada para o lote com ID: " + 1L, exception.getMessage());
    verify(avaliacaoRepository, times(1)).findAllByLoteIdWithPredicate(eq(1L), eq(predicate), eq(pageable));
  }

  @Test
  @DisplayName("Deve calcular média por lote com sucesso")
  void testCalcularMediaPorLote() {
    List<Avaliacao> avaliacoes = List.of(avaliacao);
    when(avaliacaoRepository.findAllByLoteId(1L)).thenReturn(avaliacoes);

    Double result = avaliacaoService.calcularMediaPorLote(1L);

    assertEquals(4.3f, result);
    verify(avaliacaoRepository, times(2)).findAllByLoteId(1L);
  }

  @Test
  @DisplayName("Deve buscar avaliação por ID com sucesso")
  void testBuscarPorId() {
    when(avaliacaoRepository.findById(1L)).thenReturn(Optional.of(avaliacao));

    Avaliacao result = avaliacaoService.buscarPorId(1L);

    assertNotNull(result);
    assertEquals(1L, result.getId());
    verify(avaliacaoRepository, times(1)).findById(1L);
  }

  @Test
  @DisplayName("Deve atualizar avaliação com sucesso")
  void testAtualizar() {
    Avaliacao avaliacaoAtualizada = new Avaliacao();
    avaliacaoAtualizada.setComentario("Comentário atualizado");
    avaliacaoAtualizada.setStars(4.0f);

    when(avaliacaoRepository.findById(1L)).thenReturn(Optional.of(avaliacao));
    when(avaliacaoRepository.save(any(Avaliacao.class))).thenReturn(avaliacao);

    Avaliacao result = avaliacaoService.atualizar(1L, avaliacaoAtualizada);

    assertNotNull(result);
    verify(avaliacaoRepository, times(1)).findById(1L);
    verify(avaliacaoRepository, times(1)).save(any(Avaliacao.class));
    verify(modelMapper, times(1)).map(avaliacaoAtualizada, avaliacao);
  }

  @Test
  @DisplayName("Deve excluir avaliação com sucesso")
  void testExcluir() {
    when(avaliacaoRepository.findById(1L)).thenReturn(Optional.of(avaliacao));
    doNothing().when(avaliacaoRepository).delete(any(Avaliacao.class));

    avaliacaoService.excluir(1L);

    verify(avaliacaoRepository, times(1)).findById(1L);
    verify(avaliacaoRepository, times(1)).delete(any(Avaliacao.class));
  }

}
