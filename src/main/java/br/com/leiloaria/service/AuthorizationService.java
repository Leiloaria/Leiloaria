package br.com.leiloaria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    Long currentUserId = (Long) authentication.getPrincipal();

    return usuarioService.buscarPorId(currentUserId);
  }
  
  public Boolean isUsuarioAutorizadoParaAvaliacao(Avaliacao avaliacaoExistente) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Long currentUserId = (Long) authentication.getPrincipal();

    boolean isAdmin = isAdmin();

    if (avaliacaoExistente == null) {
      return false;
    }

    return isAdmin || avaliacaoExistente.getUsuario().getId().equals(currentUserId);
  }

  public Boolean isUsuarioVencedorDoLote(Long loteId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Long currentUserId = (Long) authentication.getPrincipal();

    boolean isAdmin = isAdmin();

    if (isAdmin) return true;

    Lance maiorLance = lanceService.buscarMaiorLance(loteId);
    return maiorLance.getUsuario().getId().equals(currentUserId);
  }

  public Boolean isAdmin() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getAuthorities().stream()
        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
  }
}
