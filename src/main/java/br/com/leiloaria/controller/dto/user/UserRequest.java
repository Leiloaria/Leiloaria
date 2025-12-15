package br.com.leiloaria.controller.dto.user;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;
import org.modelmapper.ModelMapper;

import br.com.leiloaria.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    
	@NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 200, message = "O nome deve ter entre 3 e 200 caracteres")
    private String nome;
	@NotBlank(message = "O email é obrigatório")
	@Email
    private String email;
	@NotBlank(message = "O CPF é obrigatório")
	@CPF
    private String cpf;
	@NotBlank(message = "A data de nascimento é obrigatória")
    private LocalDate dataNascimento;
    private List<String> telefone;
    
 
    public Usuario convertToEntity(UserRequest usuarioRequest, ModelMapper modelMapper) {
        return modelMapper.map(usuarioRequest, Usuario.class);
    }
}
