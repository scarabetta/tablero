package ar.gob.buenosaires.dao.jpa.usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.domain.UsuarioResumen;

public interface UsuarioJpaDao extends JpaRepository<Usuario, Long> {
	
	Usuario findByEmail(String email);
	
	@Query("SELECT new ar.gob.buenosaires.domain.UsuarioResumen(u.idUsuario, u.nombre, u.apellido, u.email, u.descripcion, u.activo) FROM Usuario u")
	List<UsuarioResumen> findAllResumen();

//	@Query(value = "SELECT u.idUsuario, u.nombre, u.apellido, u.email, u.descripcion, u.activo FROM usuario u"
//			, nativeQuery = true)
//	List<Object[]> findUsuariosResumen();

}
