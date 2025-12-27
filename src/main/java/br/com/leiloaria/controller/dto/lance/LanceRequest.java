package br.com.leiloaria.controller.dto.lance;

import java.math.BigDecimal;

import br.com.leiloaria.model.Lance;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanceRequest {
    @NotBlank
    @Positive
    private BigDecimal valor;
    
    @NotBlank
    @Positive
    private Long loteId;
    
    @NotBlank
    @Positive
    private Long usuarioId;
}
