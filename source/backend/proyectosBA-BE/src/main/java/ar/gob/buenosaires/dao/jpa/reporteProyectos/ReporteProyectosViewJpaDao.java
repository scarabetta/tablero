package ar.gob.buenosaires.dao.jpa.reporteProyectos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.gob.buenosaires.domain.ReporteProyectosView;

public interface ReporteProyectosViewJpaDao extends JpaRepository<ReporteProyectosView, Long> {

	@Query(value = "SELECT r.* FROM reporte_proyectos_view r WHERE r.estado NOT IN ('Incompleto', 'Completo')", nativeQuery = true)
	public List<ReporteProyectosView> findAllParaSecretariaSinJurisdiccion();

	@Query(value = "SELECT r.* FROM reporte_proyectos_view r "
			+ " JOIN objetivo_operativo oo ON r.idObjetivoOperativo = oo.idObjetivoOperativo "
			+ " JOIN objetivo_jurisdiccional oj ON oo.idObjetivoJurisdiccional = oj.idObjetivoJurisdiccional "
			+ " JOIN jurisdiccion j ON oj.idJurisdiccion = j.idJurisdiccion "
			+ " JOIN usuario_por_jurisdiccion upj ON upj.jurisdiccion_idJurisdiccion = j.idJurisdiccion "
			+ " JOIN usuario u ON u.idUsuario = upj.usuario_idUsuario "
			+ " WHERE r.estado NOT IN ('Incompleto', 'Completo')"
			+ " AND u.email = :emailUsuario", nativeQuery = true)
	public List<ReporteProyectosView> findAllParaSecretaria(@Param("emailUsuario") String emailUsuario);

	@Query(value = "SELECT r.* FROM reporte_proyectos_view r "
			+ " JOIN objetivo_operativo oo ON r.idObjetivoOperativo = oo.idObjetivoOperativo "
			+ " JOIN objetivo_jurisdiccional oj ON oo.idObjetivoJurisdiccional = oj.idObjetivoJurisdiccional "
			+ " JOIN jurisdiccion j ON oj.idJurisdiccion = j.idJurisdiccion "
			+ " JOIN usuario_por_jurisdiccion upj ON upj.jurisdiccion_idJurisdiccion = j.idJurisdiccion "
			+ " JOIN usuario u ON u.idUsuario = upj.usuario_idUsuario "
			+" WHERE u.email = :emailUsuario", nativeQuery = true)
	public List<ReporteProyectosView> findAllParaJurisdicion(@Param("emailUsuario") String emailUsuario);
}