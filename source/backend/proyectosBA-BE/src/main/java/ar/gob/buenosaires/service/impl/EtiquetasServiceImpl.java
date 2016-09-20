package ar.gob.buenosaires.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.dao.jpa.compromisoPublico.CompromisoPublicoJpaDao;
import ar.gob.buenosaires.dao.jpa.compromisoPublico.CompromisoPublicoRepository;
import ar.gob.buenosaires.dao.jpa.temaTransversal.TemaTransversalJpaDao;
import ar.gob.buenosaires.dao.jpa.temaTransversal.TemaTransversalRepository;
import ar.gob.buenosaires.domain.EtiquetaResponse;
import ar.gob.buenosaires.otrasEtiquetas.OtrasEtiquetasJpaDao;
import ar.gob.buenosaires.otrasEtiquetas.OtrasEtiquetasRepository;
import ar.gob.buenosaires.service.EtiquetasService;

@Service
public class EtiquetasServiceImpl implements EtiquetasService {
	
	@Autowired
	private TemaTransversalRepository repositoryTemasTransversales;
	
	@Autowired
	private CompromisoPublicoRepository repositoryCompromisoPublico;
	
	@Autowired
	private OtrasEtiquetasRepository repositoryOtrasEtiquetas;
	
	@Override
	public EtiquetaResponse getEtiquetas() {
		EtiquetaResponse response = new EtiquetaResponse();
		response.setTemasTransversales(getTemaTransversalJpaDao().findByActivo(true));
		response.setCompromisosPublicos(getCompromisoPublicoJpaDao().findByActivo(true));
		response.setOtrasEtiquetas(getOtrasEtiquetasJpaDao().findAll());
		
		return response;
	}
	
	TemaTransversalJpaDao getTemaTransversalJpaDao(){
		return repositoryTemasTransversales.getTemaTransversalJpaDao();
	}
	
	CompromisoPublicoJpaDao getCompromisoPublicoJpaDao(){
		return repositoryCompromisoPublico.getCompromisoPublicoJpaDao();
	}
	
	OtrasEtiquetasJpaDao getOtrasEtiquetasJpaDao(){
		return repositoryOtrasEtiquetas.getOtrasEtiquetasJpaDao();
	}
}
