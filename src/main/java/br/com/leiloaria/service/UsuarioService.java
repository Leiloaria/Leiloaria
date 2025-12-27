package br.com.leiloaria.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

import br.com.leiloaria.model.QUsuario;
import br.com.leiloaria.model.Usuario;
import br.com.leiloaria.repository.UsuarioRepository;
import br.com.leiloaria.service.interfaces.UsuarioServiceI;

@Service
public class UsuarioService implements UsuarioServiceI {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public Page<Usuario> listar(Predicate predicate, Pageable pageable) {
		QUsuario qUsuario = QUsuario.usuario;
		BooleanBuilder filtroFixo = new BooleanBuilder();
		filtroFixo.and(qUsuario.ativo.isTrue());
		filtroFixo.and(qUsuario.instanceOf(Usuario.class)); // removi a parte de perfil, não temos essa complexidade

		Predicate predicadoFinal = filtroFixo.and(predicate);

		return usuarioRepository.findAll(predicadoFinal, pageable);
	}

	@Override
	public Usuario cadastrar(Usuario obj) {
		if (obj == null) {
			throw new IllegalArgumentException("Usuário não pode ser nulo");
		}

		if (obj.getEmail() == null || obj.getEmail().trim().isEmpty()) {
			throw new IllegalArgumentException("Email é obrigatório");
		}

		if (usuarioRepository.findByEmail(obj.getEmail()).isPresent()) {
			throw new IllegalArgumentException("Email já cadastrado");
		}

		if (obj.getNome() == null || obj.getNome().trim().isEmpty()) {
			throw new IllegalArgumentException("Nome é obrigatório");
		}

		return usuarioRepository.save(obj);
	}

	@Override
    public Usuario atualizar(Long id, Usuario obj) {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID inválido");

        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        if (obj.getEmail() != null && !obj.getEmail().equals(usuarioExistente.getEmail())) {
             if (usuarioRepository.findByEmail(obj.getEmail()).isPresent()) {
                 throw new IllegalArgumentException("Email já cadastrado");
             }
        }

        return usuarioRepository.save(usuarioExistente);
    }

	@Override
	public void excluir(Long id) {

		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

		usuarioRepository.delete(usuario);
	}

	@Override
	public Usuario buscarPorId(Long id) {

		return usuarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
	}
}
