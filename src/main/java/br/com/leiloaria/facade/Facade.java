package br.com.leiloaria.facade;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import br.com.leiloaria.controller.dto.auth.LoginRequest;
import br.com.leiloaria.controller.dto.auth.LoginResponse;
import br.com.leiloaria.controller.dto.auth.RegisterRequest;
import br.com.leiloaria.controller.dto.auth.RegisterResponse;
import br.com.leiloaria.controller.dto.user.UserRequest;
import br.com.leiloaria.model.Categoria;
import br.com.leiloaria.model.Usuario;
import br.com.leiloaria.service.interfaces.AuthServiceI;
import br.com.leiloaria.service.interfaces.CategoriaServiceI;
import br.com.leiloaria.service.interfaces.UsuarioServiceI;

@Service
public class Facade {

    @Autowired
    private AuthServiceI authService;

    @Autowired
    private CategoriaServiceI categoriaService;
    
    @Autowired
    private UsuarioServiceI userService;
    
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
 
        return userService.listar(predicate, pageable);
    }

    public Usuario atualizarUsuario(Long id, UserRequest userUpdateRequest) {
    	Usuario usuario = userUpdateRequest.convertToEntity(userUpdateRequest, modelMapper);
    	System.out.println(usuario.toString());
    	Usuario usuarioAtualizado = userService.atualizar(id, usuario);
       
        return usuarioAtualizado;
    }

    public void deletarUsuario(Long id) {
        userService.excluir(id);
    }
    
}
