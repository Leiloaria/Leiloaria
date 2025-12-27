package br.com.leiloaria.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.leiloaria.model.Usuario;
import br.com.leiloaria.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João Silva");
        usuario.setEmail("ldj@gmail.com");
        usuario.setSenha("senha123");
    }

    @Test
    @DisplayName("Deve buscar usuario por ID")
    void FindByIdSuccesss() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        Usuario resultado = usuarioService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getNome());
        assertEquals("ldj@gmail.com", resultado.getEmail());
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar excecao ao buscar usuario que não existe")
    void UserIdNotFound() {
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());
        
        assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.buscarPorId(999L);
        });
        verify(usuarioRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Deve cadastrar usuário com sucesso")
    void SaveSuccess() {
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.cadastrar(usuario);

        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getNome());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve atualizar usuário com sucesso")
    void UpdateSuccess() {
        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setId(1L);
        usuarioAtualizado.setNome("Lucas Atualizado");
        usuarioAtualizado.setEmail("ldj2@gmail.com");
        usuarioAtualizado.setSenha("novaSenha123");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioAtualizado);

        Usuario resultado = usuarioService.atualizar(1L, usuarioAtualizado);

        assertNotNull(resultado);
        assertEquals("Lucas Atualizado", resultado.getNome());
        assertEquals("ldj2@gmail.com", resultado.getEmail());
        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve excluir usuário com sucesso")
    void DeleteSucess() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        doNothing().when(usuarioRepository).delete(any(Usuario.class));

        usuarioService.excluir(1L);

        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).delete(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao excluir usuário que não existe")
    void DeleteNotFound() {
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.excluir(999L);
        });
        verify(usuarioRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar usuário com email nulo")
    void SaveEmptyEmail() {
        Usuario usuarioInvalido = new Usuario();
        usuarioInvalido.setNome("João Silva");

        assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.cadastrar(usuarioInvalido);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar usuário com nome nulo")
    void SaveEmptyName() {
        Usuario usuarioInvalido = new Usuario();
        usuarioInvalido.setEmail("ldj@gmail.com");

        assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.cadastrar(usuarioInvalido);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar usuário com email já existente")
    void SaveDuplicatedEmail() {
        when(usuarioRepository.findByEmail("ldj@gmail.com")).thenReturn(Optional.of(usuario));

        assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.cadastrar(usuario);
        });
        verify(usuarioRepository, times(1)).findByEmail("ldj@gmail.com");
    }

}
