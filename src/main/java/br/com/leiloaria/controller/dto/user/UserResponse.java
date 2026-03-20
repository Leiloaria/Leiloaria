package br.com.leiloaria.controller.dto.user;

import java.time.LocalDate;
import java.util.List;

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
    
    public UserResponse(Usuario user){
        if (user == null) throw new IllegalArgumentException("Usuario não pode ser nulo");
        this.id = user.getId();
        this.nome = user.getNome();
        this.email = user.getEmail();
        this.cpf = user.getCpf();
        this.dataNascimento = user.getDataNascimento();
        this.telefone = user.getTelefone();
        this.ativo = true;
    }

    public UserResponse() {
    }
}
