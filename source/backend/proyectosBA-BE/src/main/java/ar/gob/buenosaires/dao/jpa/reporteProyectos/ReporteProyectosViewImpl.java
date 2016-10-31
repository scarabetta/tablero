package ar.gob.buenosaires.dao.jpa.reporteProyectos;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReporteProyectosViewImpl implements ReporteProyectosViewRepository {

	@Autowired
	ReporteProyectosViewJpaDao reporteProyectosViewJpaDao;

	@Override
	public ReporteProyectosViewJpaDao getReporteProyectosViewJpaDao() {
		return reporteProyectosViewJpaDao;
	}

	@VisibleForTesting
	public void setExportadorProyectoViewJpaDao(ReporteProyectosViewJpaDao jpaDao){
		reporteProyectosViewJpaDao = jpaDao;
	}
}
