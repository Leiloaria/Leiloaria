package br.com.leiloaria.facade;

import java.util.List;

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
import br.com.leiloaria.controller.dto.user.UserResponse;
import br.com.leiloaria.model.Categoria;
import br.com.leiloaria.service.interfaces.AuthServiceI;
import br.com.leiloaria.service.interfaces.CategoriaServiceI;

@Service
public class Facade {

    @Autowired
    private AuthServiceI authService;

    @Autowired
    private CategoriaServiceI categoriaService;

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

	public UserResponse buscarUsuarioPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UserResponse> listarTodosUsuarios() {
		// TODO Auto-generated method stub
		return null;
	}

	public UserResponse atualizarUsuario(Long id, UserRequest userUpdateRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deletarUsuario(Long id) {
		// TODO Auto-generated method stub
		
	}
}
