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

	List<Proyecto> findByEstado(String estado);

	@Modifying
	@Transactional
	@Query("UPDATE Proyecto p SET p.estado = \'Presentado\' WHERE p.estado = \'En Priorizacion\' AND p.verificado = 0")
	void cancelarPriorizacionNoVerificado();

	@Modifying
	@Transactional
	@Query("UPDATE Proyecto p SET p.estado = \'Verificado\' WHERE p.estado = \'En Priorizacion\' AND p.verificado = 1")
	void cancelarPriorizacionVerificado();

	@Modifying
	@Transactional
	@Query("UPDATE Proyecto p SET p.estado = \'En Priorización\' WHERE p.estado = \'Verificado\' OR p.estado = \'Presentado\'")
	void iniciarPriorizacionDeProyectos();

	@Query("SELECT p FROM Proyecto p JOIN p.objetivoOperativo op"
			+ " join op.objetivoJurisdiccional oj join oj.jurisdiccion j"
			+ " where j.idJurisdiccion = :idJurisdiccion and p.nombre = :nombre and p.estado in (:estados)")
	Proyecto findByNombreAndIdJurisdiccionAndCiertosEstados(@Param("nombre") String nombre,
			@Param("idJurisdiccion") Long idJurisdiccion, @Param("estados") List<String> estados);

	@Query("SELECT p FROM Proyecto p JOIN p.objetivoOperativo op"
			+ " join op.objetivoJurisdiccional oj join oj.jurisdiccion j"
			+ " where j.idJurisdiccion = :idJurisdiccion and p.nombre = :nombre")
	Proyecto findByNombreAndIdJurisdiccion(@Param("nombre") String nombre,
			@Param("idJurisdiccion") Long idJurisdiccion);
}
