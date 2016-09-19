package ar.gob.buenosaires.dao.jpa.objetivoJurisdiccional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.gob.buenosaires.domain.ObjetivoJurisdiccional;

public interface ObjetivoJurisdiccionalJpaDao extends JpaRepository<ObjetivoJurisdiccional, Long> {
	
	ObjetivoJurisdiccional findByNombre(String nombre);
	
	ObjetivoJurisdiccional findByCodigo(String codigo);
	
	@Query("SELECT idObjetivoJurisdiccional FROM ObjetivoJurisdiccional obj WHERE obj.codigo LIKE(:codigo) AND obj.nombre = :nombre")
	String getObjetivosPorNombreYJurisdccion(@Param("codigo") String codigo, @Param("nombre") String nombre);
}
