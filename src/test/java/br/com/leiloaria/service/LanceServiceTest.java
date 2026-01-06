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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.leiloaria.model.Lance;
import br.com.leiloaria.repository.LanceRepository;
import br.com.leiloaria.service.exceptions.AtualizarLanceInvalidoException;
import br.com.leiloaria.service.exceptions.RecursoNaoEncontradoException;

@ExtendWith(MockitoExtension.class)
class LanceServiceTest {

    @InjectMocks
    private LanceService service;

    @Mock
    private LanceRepository repository;

    private Lance lance;

    @BeforeEach
    void setup() {
        lance = new Lance();
        lance.setId(1L);
        lance.setValor(BigDecimal.valueOf(100));
        lance.setTimestamp(LocalDateTime.now());
    }

    @Test
    void deveCadastrarLance() {
        when(repository.save(any(Lance.class)))
                .thenReturn(lance);

        Lance salvo = service.cadastrar(lance);

        assertNotNull(salvo);
        assertEquals(BigDecimal.valueOf(100), salvo.getValor());
        verify(repository).save(lance);
    }

    @Test
    void deveBuscarLancePorId() {
        when(repository.findById(1L))
                .thenReturn(Optional.of(lance));

        Lance resultado = service.buscarPorId(1L);

        assertEquals(1L, resultado.getId());
    }

    @Test
    void deveLancarExcecaoAoBuscarLanceInexistente() {
        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class,
                () -> service.buscarPorId(99L));
    }

    @Test
    void deveAtualizarValorDoLance() {
        when(repository.findById(1L))
                .thenReturn(Optional.of(lance));
        when(repository.save(any(Lance.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Lance atualizado = service.atualizarValor(1L, BigDecimal.valueOf(150));

        assertEquals(BigDecimal.valueOf(150), atualizado.getValor());
    }

    @Test
    void naoDeveAtualizarValorParaMenor() {
        when(repository.findById(1L))
                .thenReturn(Optional.of(lance));

        assertThrows(AtualizarLanceInvalidoException.class,
                () -> service.atualizarValor(1L, BigDecimal.valueOf(50)));
    }

    @Test
    void deveExcluirLance() {
        service.excluir(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void deveBuscarMaiorLancePorLote() {
        when(repository.findTopByLoteIdOrderByValorDesc(10L))
                .thenReturn(Optional.of(lance));

        Lance resultado = service.buscarMaiorLance(10L);

        assertEquals(BigDecimal.valueOf(100), resultado.getValor());
    }

    @Test
    void deveLancarExcecaoSeNaoExistirMaiorLance() {
        when(repository.findTopByLoteIdOrderByValorDesc(10L))
                .thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class,
                () -> service.buscarMaiorLance(10L));
    }

    @Test
    void deveBuscarLancesPorLote() {
        when(repository.findByLoteId(10L))
                .thenReturn(List.of(lance));

        List<Lance> lances = service.buscarLancesPorLoteId(10L);

        assertEquals(1, lances.size());
    }

    @Test
    void deveLancarExcecaoQuandoNaoHouverLancesNoLote() {
        when(repository.findByLoteId(10L))
                .thenReturn(List.of());

        assertThrows(RecursoNaoEncontradoException.class,
                () -> service.buscarLancesPorLoteId(10L));
    }
}

