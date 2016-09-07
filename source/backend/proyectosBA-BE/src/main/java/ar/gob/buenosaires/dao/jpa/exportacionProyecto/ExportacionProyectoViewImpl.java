package ar.gob.buenosaires.dao.jpa.exportacionProyecto;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExportacionProyectoViewImpl implements ExportacionProyectoViewRepository {

	@Autowired
	ExportacionProyectoViewJpaDao exportacionProyectoViewJpaDao;

	@Override
	public ExportacionProyectoViewJpaDao getExportadorProyectoViewJpaDao() {
		return exportacionProyectoViewJpaDao;
	}

	@VisibleForTesting
	public void setExportadorProyectoViewJpaDao(ExportacionProyectoViewJpaDao jpaDao){
		exportacionProyectoViewJpaDao = jpaDao;
	}
}
