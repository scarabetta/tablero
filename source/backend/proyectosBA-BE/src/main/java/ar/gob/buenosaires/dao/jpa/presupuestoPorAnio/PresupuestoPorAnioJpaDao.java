package ar.gob.buenosaires.dao.jpa.presupuestoPorAnio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.gob.buenosaires.domain.PresupuestoPorAnio;
import ar.gob.buenosaires.domain.Proyecto;

public interface PresupuestoPorAnioJpaDao extends JpaRepository<PresupuestoPorAnio, Long> {
	
	List<PresupuestoPorAnio> findByProyecto(Proyecto proyecto);
}
