package ar.gob.buenosaires.service.impl;

import java.util.List;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.dao.jpa.indicadorEstrategico.IndicadorEstrategicoJpaDao;
import ar.gob.buenosaires.dao.jpa.indicadorEstrategico.IndicadorEstrategicoRepository;
import ar.gob.buenosaires.domain.IndicadorEstrategico;
import ar.gob.buenosaires.service.IndicadorEstrategicoService;

@Service
public class IndicadorEstrategicoServiceImpl implements IndicadorEstrategicoService {

	@Autowired
	private IndicadorEstrategicoRepository repositorio;

	@Override
	public List<IndicadorEstrategico> getIndicadoresEstrategicos() {
		return getIndicadorEstrategicoDAO().findAll();
	}

	@Override
	public IndicadorEstrategico getIndicadorEstrategicoPorId(Long id) {
		return getIndicadorEstrategicoDAO().findOne(id);
	}

	@Override
	public IndicadorEstrategico createIndicadorEstrategico(IndicadorEstrategico indicadorEstrategico) {
		return getIndicadorEstrategicoDAO().save(indicadorEstrategico);
	}

	@Override
	public IndicadorEstrategico updateIndicadorEstrategico(IndicadorEstrategico indicadorEstrategico) {
		return getIndicadorEstrategicoDAO().save(indicadorEstrategico);
	}

	@Override
	public void deleteIndicadorEstrategico(IndicadorEstrategico indicadorEstrategico) {
		getIndicadorEstrategicoDAO().delete(indicadorEstrategico);
	}
	
	public IndicadorEstrategicoJpaDao getIndicadorEstrategicoDAO() {
		return repositorio.getIndicadorEstrategicoJpaDao();
	}

	@VisibleForTesting
	public void setIndicadorEstrategicoRepository(IndicadorEstrategicoRepository repo) {
		this.repositorio = repo;
	}

}
