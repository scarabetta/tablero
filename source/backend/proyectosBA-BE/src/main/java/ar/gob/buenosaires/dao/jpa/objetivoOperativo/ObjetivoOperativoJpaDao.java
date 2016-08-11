package ar.gob.buenosaires.dao.jpa.objetivoOperativo;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.gob.buenosaires.domain.ObjetivoJurisdiccional;
import ar.gob.buenosaires.domain.ObjetivoOperativo;

public interface ObjetivoOperativoJpaDao extends JpaRepository<ObjetivoOperativo, Long> {
	
	ObjetivoOperativo findByNombre(String nombre);
	
	ObjetivoOperativo findByCodigo(String codigo);
}