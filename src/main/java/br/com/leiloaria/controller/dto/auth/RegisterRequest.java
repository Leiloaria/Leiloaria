package br.com.leiloaria.controller.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "O username é obrigatorio")
    private String username;

    @NotBlank(message = "O senha é obrigatorio")
    @Size(min = 6, message = "A senha deve ter no minimo 6 caracteres")
    private String password;

    @NotBlank(message = "O email é obrigatorio")
    @Email(message = "Email invalido")
    private String email;

}
