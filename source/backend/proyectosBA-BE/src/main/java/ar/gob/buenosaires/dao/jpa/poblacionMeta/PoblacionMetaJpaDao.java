package ar.gob.buenosaires.dao.jpa.poblacionMeta;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.gob.buenosaires.domain.PoblacionMeta;

public interface PoblacionMetaJpaDao extends JpaRepository<PoblacionMeta, Long> {
	/*
	 * Agregar metodos custom que se vayan a utilizar
	 */
}
