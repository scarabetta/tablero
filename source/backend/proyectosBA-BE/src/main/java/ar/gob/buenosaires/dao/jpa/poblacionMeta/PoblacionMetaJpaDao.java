package ar.gob.buenosaires.dao.jpa.poblacionMeta;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.gob.buenosaires.domain.PoblacionMeta;

public interface PoblacionMetaJpaDao extends JpaRepository<PoblacionMeta, Long> {

	PoblacionMeta findByNombre(String nombre);
}
