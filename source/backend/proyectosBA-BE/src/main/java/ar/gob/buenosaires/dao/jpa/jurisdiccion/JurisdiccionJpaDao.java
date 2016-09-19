package ar.gob.buenosaires.dao.jpa.jurisdiccion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.gob.buenosaires.domain.Jurisdiccion;

public interface JurisdiccionJpaDao extends JpaRepository<Jurisdiccion, Long> {
	
	Jurisdiccion findByNombre(String nombre);
	
	Jurisdiccion findByCodigo(String codigo);

	@Query("SELECT distinct j FROM Jurisdiccion j LEFT JOIN j.objetivosJurisdiccionales oj"
	+ " LEFT JOIN oj.objetivosOperativos op LEFT JOIN op.proyectos p"
	+ " WHERE p.estado IN (\'Verificado\')")	
	List<Jurisdiccion> findAllParaSecretaria();

}

//@Query("SELECT p FROM Proyecto p JOIN p.objetivoOperativo op"
//		+ " join op.objetivoJurisdiccional oj join oj.jurisdiccion j"
//		+ " where j.idJurisdiccion = :idJurisdiccion and p.nombre = :nombre and p.estado in (\'Completo\', \'Incompleto\', \'Presentado\')")
//Proyecto findByNombreAndIdJurisdiccion(@Param("nombre") String nombre, @Param("idJurisdiccion") Long idJurisdiccion);

//+ " WHERE  p.estado NOT IN (\'Completo\', \'Incompleto\')")

//@Query("SELECT j FROM Jurisdiccion j JOIN j.objetivosJurisdiccionales oj"
//		+ " JOIN oj.objetivosOperativos op JOIN op.proyectos p"
//		+ " WHERE  p.estado NOT IN (\'Completo\', \'Incompleto\')")	