package ar.gob.buenosaires.dao.jpa.objetivoJurisdiccional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.gob.buenosaires.domain.ObjetivoJurisdiccional;

public interface ObjetivoJurisdiccionalJpaDao extends JpaRepository<ObjetivoJurisdiccional, Long> {
	
	ObjetivoJurisdiccional findByNombre(String nombre);
	
	ObjetivoJurisdiccional findByCodigo(String codigo);
}
