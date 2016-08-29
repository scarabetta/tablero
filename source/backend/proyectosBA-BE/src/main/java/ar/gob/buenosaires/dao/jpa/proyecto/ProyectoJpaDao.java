package ar.gob.buenosaires.dao.jpa.proyecto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import ar.gob.buenosaires.domain.Proyecto;

public interface ProyectoJpaDao extends JpaRepository<Proyecto, Long> {
	
	Proyecto findByNombre(String nombre);
	
	Proyecto findByCodigo(String codigo);
	
	@Modifying
	@Transactional
	@Query("UPDATE Proyecto p SET p.estado = \'Presentado\' WHERE p.idProyecto in (:proyectosId)")
	void updateProyectosCompletos(@Param("proyectosId") List<Long> proyectosId);
		
}
