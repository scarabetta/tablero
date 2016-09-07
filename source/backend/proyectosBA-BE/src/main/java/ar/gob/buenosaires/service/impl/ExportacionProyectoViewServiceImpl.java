package ar.gob.buenosaires.service.impl;

import java.util.List;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.dao.jpa.exportacionProyecto.ExportacionProyectoViewJpaDao;
import ar.gob.buenosaires.dao.jpa.exportacionProyecto.ExportacionProyectoViewRepository;
import ar.gob.buenosaires.domain.ExportacionProyectoView;
import ar.gob.buenosaires.service.ExportacionProyectoViewService;

@Service
public class ExportacionProyectoViewServiceImpl implements ExportacionProyectoViewService {

	@Autowired
	private ExportacionProyectoViewRepository repositorio;

	@Override
	public List<ExportacionProyectoView> getAllExportacionProyectoView() {
		return getExportadorProyectoViewJpaDaoDAO().findAll();
	}

	public ExportacionProyectoViewJpaDao getExportadorProyectoViewJpaDaoDAO() {
		return repositorio.getExportadorProyectoViewJpaDao();
	}

	@VisibleForTesting
	public void setExportacionProyectoViewRepositoryRepository(ExportacionProyectoViewRepository repo) {
		repositorio = repo;
	}

}
