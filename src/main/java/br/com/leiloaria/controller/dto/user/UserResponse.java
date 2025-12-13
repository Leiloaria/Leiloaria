package br.com.leiloaria.controller.dto.user;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;

import br.com.leiloaria.model.Usuario;
import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private LocalDate dataNascimento;
    private List<String> telefone;
    private boolean ativo;
    
    public UserResponse(Usuario user, ModelMapper modelMapper){
        if (user == null) throw new IllegalArgumentException("Usuario não pode ser nulo");
        else modelMapper.map(user, this);
    }

    public UserResponse() {
    }
}
