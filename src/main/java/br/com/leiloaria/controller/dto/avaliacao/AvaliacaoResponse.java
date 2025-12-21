package br.com.leiloaria.controller.dto.avaliacao;

import org.modelmapper.ModelMapper;
import br.com.leiloaria.model.Avaliacao;
import lombok.Data;

@Data
public class AvaliacaoResponse {
  private Long id;
  private String comentario;
  private Float stars; // Isso pode ser aquelas notas 4.3, etc. Não sei bem ainda

  public AvaliacaoResponse(Avaliacao avaliacao, ModelMapper modelMapper) {
    if (avaliacao == null)
      throw new IllegalArgumentException("Avaliacao não pode ser nulo");
    else
      modelMapper.map(avaliacao, this);
  }

  public AvaliacaoResponse() {
  }

}
