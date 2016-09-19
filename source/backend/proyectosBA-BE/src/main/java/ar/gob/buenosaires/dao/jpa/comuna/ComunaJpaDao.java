package ar.gob.buenosaires.dao.jpa.comuna;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.gob.buenosaires.domain.Comuna;

public interface ComunaJpaDao extends JpaRepository<Comuna, Long> {

	Comuna findByNombre(String nombre);

}