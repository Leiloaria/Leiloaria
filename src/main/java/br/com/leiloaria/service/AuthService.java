package br.com.leiloaria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.leiloaria.controller.dto.auth.LoginRequest;
import br.com.leiloaria.controller.dto.auth.LoginResponse;
import br.com.leiloaria.controller.dto.auth.RegisterRequest;
import br.com.leiloaria.controller.dto.auth.RegisterResponse;
import br.com.leiloaria.model.Usuario;
import br.com.leiloaria.repository.UsuarioRepository;
import br.com.leiloaria.service.interfaces.AuthServiceI;

@Service
public class AuthService implements AuthServiceI {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Usuario u = usuarioRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com este e-mail."));

        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(), loginRequest.getPassword());

            Authentication auth = authenticationManager.authenticate(usernamePassword);

            String token = tokenService.gerarToken(auth);

            return new LoginResponse(token);

        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException("Senha incorreta, tente outra.");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao realizar login: " + e.getMessage());
        }
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        if (usuarioRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(registerRequest.getUsername());
        novoUsuario.setEmail(registerRequest.getEmail());
        novoUsuario.setSenha(passwordEncoder.encode(registerRequest.getPassword()));
        novoUsuario.setAtivo(true);

        usuarioRepository.save(novoUsuario);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                novoUsuario.getEmail(), registerRequest.getPassword(), novoUsuario.getAuthorities());
        String token = tokenService.gerarToken(authentication);

        return new RegisterResponse("Usuário registrado com sucesso", token);
    }
}
