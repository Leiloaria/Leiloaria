package br.com.leiloaria.controller.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    
    @NotBlank(message = "O email é obrigatorio")
    @Email(message = "Email invalido")
    private String email;

    @NotBlank(message = "A senha é obrigatoria")
    private String password;

}
