package ar.gob.buenosaires.dao.jpa.tipoObra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.gob.buenosaires.domain.TipoObra;

public interface TipoObraJpaDao extends JpaRepository<TipoObra, Long> {

	@Query("SELECT o FROM TipoObra o LEFT JOIN o.subtiposObra s"
			+ " where s.idSubtipoObra = :subtipoObraId")
	TipoObra findBySubtipoObra(@Param("subtipoObraId") Long subtipoObraId);

}
