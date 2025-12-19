package br.com.leiloaria.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import com.querydsl.core.types.Predicate;

import br.com.leiloaria.controller.dto.user.UserRequest;
import br.com.leiloaria.controller.dto.user.UserResponse;
import br.com.leiloaria.facade.Facade;
import br.com.leiloaria.model.Leilao;
import br.com.leiloaria.model.Usuario;

@RestController
@RequestMapping("/users")
public class UserController {
	
    @Autowired
    private Facade facade;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Usuario usuario = facade.buscarUsuarioPorId(id);
        return new ResponseEntity<>(new UserResponse(usuario, modelMapper), HttpStatus.OK);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public Page<UserResponse> listarTodos(
            @QuerydslPredicate(root = Usuario.class) Predicate predicate,
            @PageableDefault(value = 2)
            @SortDefault(sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable) {

        return facade.listarTodosUsuarios(predicate, pageable).map(usuario -> new UserResponse(usuario, modelMapper));

    }
    
    @GetMapping("/{id}/leiloes")
    public List<Leilao> listarTodosLeiloes(@PathVariable("id") Long idLeilao) {
        return facade.buscarLeiloesPorUsuario(idLeilao);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN')") // TODO: o proprio user pode atualuizar seu perfil
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody UserRequest userUpdateRequest) {
        Usuario userAtualizado = facade.atualizarUsuario(id, userUpdateRequest);
        return new ResponseEntity<>(new UserResponse(userAtualizado, modelMapper), HttpStatus.OK);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN')") // TODO: o proprio user pode apagar seu perfil
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        facade.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}