package br.com.leiloaria.controller.dto.avaliacao;

import org.modelmapper.ModelMapper;

import br.com.leiloaria.controller.dto.user.UserResponse;
import br.com.leiloaria.model.Avaliacao;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AvaliacaoResponse {
  private Long id;
  private String comentario;
  private Float stars;
  private UserResponse usuario;

  public AvaliacaoResponse(Avaliacao avaliacao, ModelMapper modelMapper) {
    if (avaliacao == null)
      throw new IllegalArgumentException("Avaliacao não pode ser nulo");
    else
      modelMapper.map(avaliacao, this);
    this.usuario = new UserResponse(avaliacao.getUsuario());
  }

}
