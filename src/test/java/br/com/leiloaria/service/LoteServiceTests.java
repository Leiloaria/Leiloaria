package br.com.leiloaria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.leiloaria.model.Leilao;
import br.com.leiloaria.model.Lote;
import br.com.leiloaria.model.enums.StatusLeilao;
import br.com.leiloaria.repository.LeilaoRepository;
import br.com.leiloaria.repository.LoteRepository;
import br.com.leiloaria.service.exceptions.RecursoNaoEncontradoException;

@ExtendWith(MockitoExtension.class)
@DisplayName("LoteService Tests")
public class LoteServiceTests {

    @InjectMocks
    private LoteService service;
	
    @Mock
    private LoteRepository repository;

    private Lote lote;

    @BeforeEach
    void setup() {
        lote = new Lote();
        lote.setId(1L);
    }
    
    @Test
    void deveBuscarLeilaoPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(lote));

        Lote resultado = service.buscarLoteById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void deveLancarExcecaoAoBuscarLeilaoInexistente() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class,
                () -> service.buscarLoteById(1L));
    }
}
