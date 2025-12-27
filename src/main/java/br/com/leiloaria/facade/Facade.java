package br.com.leiloaria.facade;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import br.com.leiloaria.controller.dto.user.UserRequest;
import br.com.leiloaria.model.Avaliacao;
import br.com.leiloaria.model.Categoria;
import br.com.leiloaria.model.Usuario;
import br.com.leiloaria.service.AuthorizationService;
import br.com.leiloaria.service.interfaces.AuthServiceI;
import br.com.leiloaria.service.interfaces.AvaliacaoServiceI;
import br.com.leiloaria.service.interfaces.CategoriaServiceI;
import br.com.leiloaria.service.interfaces.UsuarioServiceI;

@Service
public class Facade {

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
    private ModelMapper modelMapper;

    // CATEGORIAS
    public Page<Categoria> listarCategorias(Predicate filtro, Pageable pageable) {
        return categoriaService.listar(filtro, pageable);
    }

    public Categoria cadastrarCategoria(Categoria obj) {
        return categoriaService.cadastrar(obj);
    }

    public Categoria atualizarCategoria(Long id, Categoria obj) {
        return categoriaService.atualizar(id, obj);
    }

    public void excluirCategoria(Long id) {
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

        if (authorizationService.isUsuarioAutorizadoParaAvaliacao(avaliacaoExistente)) {
            throw new AccessDeniedException(
                    "Apenas o administrador ou o usuário que fez a avaliação pode atualizá-la.");
        }

        Avaliacao avaliacao = avaliacaoRequest.convertToEntity(avaliacaoRequest, modelMapper);
        return avaliacaoService.atualizar(id, avaliacao);
    }

    public void deletarAvaliacao(Long id) {
        Avaliacao avaliacaoExistente = avaliacaoService.buscarPorId(id);

        if (authorizationService.isUsuarioAutorizadoParaAvaliacao(avaliacaoExistente)) {
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

        // Verificar se o lote existe
        // Verificar se o usuario ja avaliou o lote(dentro do service já faz) - ok
        // Verificar se o leilao já finalizou (pq ai ta aberto a avaliacao)
        // verificar se o usuario da sessao é o mesmo que comprou.
        

        Avaliacao avaliacao = avaliacaoRequest.convertToEntity(avaliacaoRequest, modelMapper);

        return avaliacaoService.avaliarCompra(avaliacao);
    }

    

}
