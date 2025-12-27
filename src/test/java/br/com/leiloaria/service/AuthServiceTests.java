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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.leiloaria.controller.dto.auth.LoginRequest;
import br.com.leiloaria.controller.dto.auth.LoginResponse;
import br.com.leiloaria.controller.dto.auth.RegisterRequest;
import br.com.leiloaria.controller.dto.auth.RegisterResponse;
import br.com.leiloaria.model.Usuario;
import br.com.leiloaria.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {

  @Mock
  private UsuarioRepository usuarioRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private TokenService tokenService;

  @InjectMocks
  private AuthService authService;

  private Usuario usuario;
  private LoginRequest loginRequest;
  private RegisterRequest registerRequest;

  @BeforeEach
  void setUp() {
    usuario = new Usuario();
    usuario.setId(1L);
    usuario.setNome("João Silva");
    usuario.setEmail("ldj@gmail.com");
    usuario.setSenha("senha123");
    usuario.setAtivo(true);

    loginRequest = new LoginRequest("ldj@gmail.com", "senha123");
    registerRequest = new RegisterRequest("João Silva", "senha123", "ldj@gmail.com");
  }

  @Test
  @DisplayName("Deve realizar login com sucesso")
  void testLoginSuccess() {
    Authentication auth = mock(Authentication.class);

    when(usuarioRepository.findByEmail("ldj@gmail.com")).thenReturn(Optional.of(usuario));
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
    when(tokenService.gerarToken(auth)).thenReturn("token123");

    LoginResponse response = authService.login(loginRequest);

    assertNotNull(response);
    assertEquals("token123", response.getToken());
  }

  @Test
  @DisplayName("Deve registrar usuário com sucesso")
  void testRegisterSuccess() {

    when(usuarioRepository.findByEmail("ldj@gmail.com")).thenReturn(Optional.empty());
    when(passwordEncoder.encode("senha123")).thenReturn("encodedSenha");
    when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
    when(tokenService.gerarToken(any(Authentication.class))).thenReturn("token123");

    RegisterResponse response = authService.register(registerRequest);

    assertNotNull(response);
    assertEquals("Usuário registrado com sucesso", response.getMessage());
  }

}
