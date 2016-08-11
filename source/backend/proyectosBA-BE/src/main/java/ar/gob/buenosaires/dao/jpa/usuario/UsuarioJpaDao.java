package ar.gob.buenosaires.dao.jpa.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.gob.buenosaires.domain.Usuario;

public interface UsuarioJpaDao extends JpaRepository<Usuario, Long> {
	
	Usuario findByEmail(String email);

}
