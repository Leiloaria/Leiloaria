package br.com.leiloaria.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.leiloaria.model.Avaliacao;

@Service
public class AuthorizationService {
  
  public Boolean isUsuarioAutorizadoParaAvaliacao(Avaliacao avaliacaoExistente) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Long currentUserId = (Long) authentication.getPrincipal();

    boolean isAdmin = isAdmin();

    return isAdmin || avaliacaoExistente.getUsuario().getId().equals(currentUserId);
  }

  public Boolean isAdmin() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getAuthorities().stream()
        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
  }
}
