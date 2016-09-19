package ar.gob.buenosaires.dao.jpa.objetivoOperativo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.gob.buenosaires.domain.ObjetivoOperativo;

public interface ObjetivoOperativoJpaDao extends JpaRepository<ObjetivoOperativo, Long> {
	
	ObjetivoOperativo findByNombre(String nombre);
	
	ObjetivoOperativo findByCodigo(String codigo);
	
	@Query("SELECT idObjetivoOperativo FROM ObjetivoOperativo obj WHERE obj.codigo LIKE(:codigo) AND obj.nombre = :nombre")
	String getObjetivosPorNombreYJurisdccion(@Param("codigo") String codigo, @Param("nombre") String nombre);
}