package ar.gob.buenosaires.dao.jpa.proyecto;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.gob.buenosaires.domain.ObjetivoJurisdiccional;
import ar.gob.buenosaires.domain.Proyecto;

public interface ProyectoJpaDao extends JpaRepository<Proyecto, Long> {
	
	Proyecto findByNombre(String nombre);
	
	Proyecto findByCodigo(String codigo);
}
