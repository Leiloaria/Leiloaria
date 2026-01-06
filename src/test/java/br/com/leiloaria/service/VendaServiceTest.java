package br.com.leiloaria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.leiloaria.model.Lance;
import br.com.leiloaria.model.Pagamento;
import br.com.leiloaria.model.PagamentoPix;
import br.com.leiloaria.model.Venda;
import br.com.leiloaria.model.enums.StatusPagamento;
import br.com.leiloaria.repository.VendaRepository;
import br.com.leiloaria.service.exceptions.RecursoNaoEncontradoException;

@ExtendWith(MockitoExtension.class)
class VendaServiceTest {

    @InjectMocks
    private VendaService service;

    @Mock
    private VendaRepository repository;

    private Lance lance;
    private Pagamento pagamento;

    @BeforeEach
    void setup() {
        lance = new Lance();
        lance.setId(1L);
        lance.setValor(BigDecimal.valueOf(500));

        pagamento = new PagamentoPix();
        pagamento.setStatus(StatusPagamento.PENDING);
    }


    @Test
    void deveGerarVendaComLanceEPagamento() {
        when(repository.save(any(Venda.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Venda venda = service.gerarVenda(lance, pagamento);

        assertNotNull(venda);
        assertEquals(BigDecimal.valueOf(500), venda.getValor());
        assertEquals(lance, venda.getLance());
        assertEquals(pagamento, venda.getMetodoPagamento());

        verify(repository).save(any(Venda.class));
    }


    @Test
    void deveBuscarVendaPorId() {
        Venda venda = new Venda();
        venda.setId(10L);

        when(repository.findById(10L))
                .thenReturn(Optional.of(venda));

        Venda encontrada = service.buscarPorId(10L);

        assertEquals(10L, encontrada.getId());
    }

    @Test
    void deveLancarExcecaoQuandoVendaNaoExiste() {
        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class,
                () -> service.buscarPorId(99L));
    }

    @Test
    void deveAtualizarStatusPagamentoDaVenda() {
        Venda venda = new Venda();
        venda.setId(1L);
        venda.setMetodoPagamento(pagamento);

        when(repository.findById(1L))
                .thenReturn(Optional.of(venda));
        when(repository.save(any(Venda.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Venda atualizada = service.atualizarStatusPagamentoVenda(1L, StatusPagamento.PAID);

        assertEquals(StatusPagamento.PAID, atualizada.getMetodoPagamento().getStatus());
    }
}

