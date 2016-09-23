package ar.gob.buenosaires.dao.jpa.errorDescripcion;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.gob.buenosaires.domain.ErrorDescripcion;

public interface ErrorDescripcionJpaDao extends JpaRepository<ErrorDescripcion, Long> {
	
}