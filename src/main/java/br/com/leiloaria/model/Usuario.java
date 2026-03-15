package br.com.leiloaria.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(unique = true)
    private String cpf;
    @Column(unique = true)
    private String email;
    private List<String> telefone;
    private Boolean ativo = true;
    @Column(nullable = false)
    private String senha;
    private LocalDate dataNascimento;
    private Boolean ehAdmin = false;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Endereco endereco;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Leilao> leiloes;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lance> lances;
    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	if(ehAdmin) {
    		return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    	}
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.ativo;
    }
}
