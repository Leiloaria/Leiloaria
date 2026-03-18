package br.com.leiloaria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import br.com.leiloaria.model.Avaliacao;
import br.com.leiloaria.model.Lance;
import br.com.leiloaria.model.Usuario;
import br.com.leiloaria.service.interfaces.LanceServiceI;
import br.com.leiloaria.service.interfaces.UsuarioServiceI;

@Service
public class AuthorizationService {

  @Autowired
  private UsuarioServiceI usuarioService;

  @Autowired
  private LanceServiceI lanceService;

  public Usuario getUsuarioLogado() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication.getPrincipal() instanceof Jwt jwt) {
      try {
        Usuario logado = usuarioService.buscarPorEmail(jwt.getSubject());
        return logado;
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Erro de autorização");
      }
    } else {
      throw new IllegalArgumentException("Erro de autorização");
    }
  }

  public Boolean isUsuarioAutorizadoParaAvaliacao(Avaliacao avaliacaoExistente) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Long currentUserId = null;
    if (authentication.getPrincipal() instanceof Jwt jwt) {
      Usuario logado = usuarioService.buscarPorEmail(jwt.getSubject());
      if (logado == null)
        return false;
      currentUserId = logado.getId();
    }

    boolean isAdmin = isAdmin();

    if (avaliacaoExistente == null) {
      return false;
    }

    return isAdmin || avaliacaoExistente.getUsuario().getId().equals(currentUserId);
  }

  public Boolean isUsuarioVencedorDoLote(Long loteId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    Long currentUserId = null;
    if (authentication.getPrincipal() instanceof Jwt jwt) {
      Usuario logado = usuarioService.buscarPorEmail(jwt.getSubject());
      if (logado == null)
        return false;
      currentUserId = logado.getId();
    }

    if (isAdmin())
      return true;

    if (currentUserId == null)
      return false;

    Lance maiorLance = lanceService.buscarMaiorLance(loteId);

    if (maiorLance == null) {
      return false;
    }

    return maiorLance.getUsuario().getId().equals(currentUserId);
  }

  public Boolean isAdmin() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getAuthorities().stream()
        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
  }
}
