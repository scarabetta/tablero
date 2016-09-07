package ar.gob.buenosaires.dao.jpa.exportacionProyecto;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.gob.buenosaires.domain.ExportacionProyectoView;

public interface ExportacionProyectoViewJpaDao extends JpaRepository<ExportacionProyectoView, Long> {

	//List<ExportacionProyectoView> getAllExportacionProyectoView();

}