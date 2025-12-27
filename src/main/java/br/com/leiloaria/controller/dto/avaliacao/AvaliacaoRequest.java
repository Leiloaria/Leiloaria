package br.com.leiloaria.controller.dto.avaliacao;

import org.modelmapper.ModelMapper;

import br.com.leiloaria.model.Avaliacao;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoRequest {

  @Size(max = 200, message = "O nome deve ter entre 3 e 200 caracteres") // ta liberado avaliar sem dizer nada :D, todos agradecem
  private String comentario;

  @NotNull(message = "A nota é obrigatória")
  @Min(value = 0, message = "A nota mínima é 0")
  @Max(value = 5, message = "A nota máxima é 5")
  private Float stars; // nao existe avaliação sem nota

  @NotNull(message = "O ID do lote é obrigatório")
  private Long loteId;

  public Avaliacao convertToEntity(AvaliacaoRequest avaliacaoRequest, ModelMapper modelMapper) {
    return modelMapper.map(avaliacaoRequest, Avaliacao.class);
  }
}
