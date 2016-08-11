package ar.gob.buenosaires.dao.jpa.ejeDeGobierno;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.gob.buenosaires.domain.EjeDeGobierno;

public interface EjeDeGobiernoJpaDao extends JpaRepository<EjeDeGobierno, Long> {

	EjeDeGobierno findByNombre(String nombre);
}
