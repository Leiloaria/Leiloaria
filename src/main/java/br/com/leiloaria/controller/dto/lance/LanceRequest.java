package br.com.leiloaria.controller.dto.lance;

import java.math.BigDecimal;

import br.com.leiloaria.model.Lance;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanceRequest {
    @NotNull
    @Positive
    private BigDecimal valor;
    
    @NotNull
    @Positive
    private Long loteId;
    
    @NotNull
    @Positive
    private Long usuarioId;
}
