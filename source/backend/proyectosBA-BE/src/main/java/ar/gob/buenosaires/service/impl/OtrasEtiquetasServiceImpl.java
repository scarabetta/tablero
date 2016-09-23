package ar.gob.buenosaires.service.impl;

import java.util.List;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.domain.OtraEtiqueta;
import ar.gob.buenosaires.esb.exception.CodigoError;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.otrasEtiquetas.OtrasEtiquetasJpaDao;
import ar.gob.buenosaires.otrasEtiquetas.OtrasEtiquetasRepository;
import ar.gob.buenosaires.otrasEtiquetas.OtrasEtiquetasRepositoryImpl;
import ar.gob.buenosaires.service.OtrasEtiquetasService;

@Service
public class OtrasEtiquetasServiceImpl implements OtrasEtiquetasService {
	
	@Autowired
	private OtrasEtiquetasRepository repository;

	@Override
	public OtraEtiqueta createOtrasEtiquetas(OtraEtiqueta otrasEtiquetas) throws ESBException {
		OtraEtiqueta OtrasEtiquetasNombre = getOtrasEtiquetasJpaDao().findByEtiqueta(otrasEtiquetas.getEtiqueta());
		if (OtrasEtiquetasNombre == null) {
			return getOtrasEtiquetasJpaDao().save(otrasEtiquetas);
		} else {
			throw new ESBException(CodigoError.ETIQUETA_REPETIDA.getCodigo(), "Ya existe una etiqueta con nombre: " + otrasEtiquetas.getEtiqueta());
		}				
	}

	@Override
	public OtraEtiqueta updateOtrasEtiquetas(OtraEtiqueta otrasEtiquetas) throws ESBException {
		OtraEtiqueta OtrasEtiquetasNombre = getOtrasEtiquetasJpaDao().findByEtiqueta(otrasEtiquetas.getEtiqueta());
		if (OtrasEtiquetasNombre != null && OtrasEtiquetasNombre.getIdEtiqueta() != otrasEtiquetas.getIdEtiqueta()) {
			throw new ESBException(CodigoError.ETIQUETA_REPETIDA.getCodigo(),  "Ya existe una etiqueta con nombre: " + otrasEtiquetas.getEtiqueta());
		} else {
			return getOtrasEtiquetasJpaDao().save(otrasEtiquetas);
		}
	}

	@Override
	public void deleteOtrasEtiquetas(Long id) throws ESBException {
		OtraEtiqueta otrasEtiquetas = getOtrasEtiquetasJpaDao().findOne(id);
		if (otrasEtiquetas != null) {
			getOtrasEtiquetasJpaDao().delete(otrasEtiquetas);
		} else {
			throw new ESBException(CodigoError.ETIQUETA_INEXISTENTE.getCodigo(), "No se encontro etiqueta con id: " + id);
		}		
	}

	@Override
	public OtraEtiqueta getOtrasEtiquetasPorId(Long id) {
		return getOtrasEtiquetasJpaDao().findOne(id);
	}

	@Override
	public List<OtraEtiqueta> getOtrasEtiquetas() {
		return getOtrasEtiquetasJpaDao().findAll();
	}
	
	OtrasEtiquetasJpaDao getOtrasEtiquetasJpaDao(){
		return repository.getOtrasEtiquetasJpaDao();
	}
	
	@VisibleForTesting
	public void setOtrasEtiquetasRepository(OtrasEtiquetasRepositoryImpl repo) {
		this.repository = repo;
	}
}
