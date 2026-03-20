package br.com.leiloaria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.leiloaria.model.Categoria;
import br.com.leiloaria.repository.CategoriaRepository;
import br.com.leiloaria.service.exceptions.RecursoNaoEncontradoException;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @InjectMocks
    private CategoriaService service;

    @Mock
    private CategoriaRepository repository;

    private Categoria categoria;

    @BeforeEach
    void setup() {
        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Eletrônicos");
        categoria.setSubcategorias(new ArrayList());
    }

    // ===================== cadastrar =====================

    @Test
    void deveCadastrarCategoria() {
        when(repository.save(any(Categoria.class)))
                .thenReturn(categoria);

        Categoria salva = service.cadastrar(categoria);

        assertNotNull(salva);
        assertEquals("Eletrônicos", salva.getNome());
        verify(repository).save(categoria);
    }

    // ===================== buscarPorId =====================

    @Test
    void deveBuscarCategoriaPorId() {
        when(repository.findById(1L))
                .thenReturn(Optional.of(categoria));

        Categoria resultado = service.buscarPorId(1L);

        assertEquals("Eletrônicos", resultado.getNome());
    }

    @Test
    void deveLancarExcecaoAoBuscarCategoriaInexistente() {
        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class,
                () -> service.buscarPorId(99L));
    }

    // ===================== atualizar =====================

    @Test
    void deveAtualizarCategoria() {
        Categoria nova = new Categoria();
        nova.setNome("Eletrônicos e Informática");

        when(repository.findById(1L))
                .thenReturn(Optional.of(categoria));
        when(repository.save(any(Categoria.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Categoria atualizada = service.atualizar(1L, nova);

        assertEquals("Eletrônicos e Informática", atualizada.getNome());
    }

    // ===================== excluir =====================

    @Test
    void deveExcluirCategoria() {
        when(repository.findById(1L))
                .thenReturn(Optional.of(categoria));

        service.excluir(1L);

        verify(repository).delete(categoria);
    }

    @Test
    void naoDeveExcluirCategoriaInexistente() {
        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class,
                () -> service.excluir(1L));
    }

    // ===================== addSubCategoria =====================

    @Test
    void deveAdicionarSubcategoria() {
        Categoria sub = new Categoria();
        sub.setNome("Celulares");

        when(repository.findById(1L))
                .thenReturn(Optional.of(categoria));
        when(repository.save(any(Categoria.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Categoria resultado = service.addSubCategoria(1L, sub);

        assertEquals(1, resultado.getSubcategorias().size());
        assertEquals("Celulares", resultado.getSubcategorias().get(0).getNome());
    }
}

