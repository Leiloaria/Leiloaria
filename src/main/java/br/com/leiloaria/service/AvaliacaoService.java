package br.com.leiloaria.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import br.com.leiloaria.model.Avaliacao;
import br.com.leiloaria.repository.AvaliacaoRepository;
import br.com.leiloaria.service.interfaces.AvaliacaoServiceI;

@Service
public class AvaliacaoService implements AvaliacaoServiceI {

  @Autowired
  private AvaliacaoRepository repository;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public Avaliacao avaliarCompra(Avaliacao avaliacao) {

    if (jaAvaliou(avaliacao.getUsuario().getId(), avaliacao.getLote().getId())) {
      throw new IllegalArgumentException("Usuário já avaliou este lote.");
    }
    

    Avaliacao avaliacaoSalva = repository.save(avaliacao);

    return avaliacaoSalva;
  }

  @Override
  public Page<Avaliacao> listarPorLote(Long idLote, Predicate predicate, Pageable pageable) {
    
    if (repository.findAllByLoteIdWithPredicate(idLote, predicate, pageable).isEmpty()) {
      throw new IllegalArgumentException("Nenhuma avaliação encontrada para o lote com ID: " + idLote);
    }

    return repository.findAllByLoteIdWithPredicate(idLote, predicate, pageable);
  }

  @Override
  public Double calcularMediaPorLote(Long idLote) {

    if (repository.findAllByLoteId(idLote).isEmpty()) {
      throw new IllegalArgumentException("Nenhuma avaliação encontrada para o lote com ID: " + idLote);
    }

    return repository.findAllByLoteId(idLote)
        .stream()
        .mapToDouble(Avaliacao::getStars)
        .average()
        .orElse(0.0);
  }

  @Override
  public Avaliacao buscarPorId(Long id) {
    return repository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Avaliação não encontrada com ID: " + id));
  }

  @Override
  public Page<Avaliacao> listar(Predicate predicate, Pageable pageable) {
    return repository.findAll(predicate, pageable);
  }

  @Override
  public Avaliacao atualizar(Long id, Avaliacao avaliacao) {

    Avaliacao avaliacaoExistente = repository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Avaliação não encontrada com ID: " + id));

    modelMapper.map(avaliacao, avaliacaoExistente);
    return repository.save(avaliacaoExistente);
  }

  @Override
  public void excluir(Long id) {

    Avaliacao avaliacaoExistente = repository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Avaliação não encontrada com ID: " + id));

    repository.delete(avaliacaoExistente);
  }

  private boolean jaAvaliou(Long idUsuario, Long idLote) {
    return repository.existsByUsuarioIdAndLoteId(idUsuario, idLote);
  }

}
