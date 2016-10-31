package ar.gob.buenosaires.service.impl;

import java.util.List;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.dao.jpa.reporteProyectos.ReporteProyectosViewJpaDao;
import ar.gob.buenosaires.dao.jpa.reporteProyectos.ReporteProyectosViewRepository;
import ar.gob.buenosaires.domain.ReporteProyectosView;
import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.service.ReporteProyectosViewService;

@Service
public class ReporteProyectosViewServiceImpl implements ReporteProyectosViewService {

	@Autowired
	private ReporteProyectosViewRepository repositorio;

	@Override
	public List<ReporteProyectosView> getAllReporteProyectosView(Usuario user) {
		List<ReporteProyectosView> reporte;
		if (user != null && user.tienePerfilSecretaria()) {
			if (user.getJurisdicciones().isEmpty()) {
				reporte = getReporteProyectosViewJpaDao().findAllParaSecretariaSinJurisdiccion();
			} else {
				reporte = getReporteProyectosViewJpaDao().findAllParaSecretaria();
			}
		} else {
			reporte = getReporteProyectosViewJpaDao().findAllParaJurisdicion();
		}
		return reporte;
	}

	public ReporteProyectosViewJpaDao getReporteProyectosViewJpaDao() {
		return repositorio.getReporteProyectosViewJpaDao();
	}

	@VisibleForTesting
	public void setExportacionProyectoViewRepositoryRepository(ReporteProyectosViewRepository repo) {
		repositorio = repo;
	}

}
