package ar.gob.buenosaires.service.impl;

import java.util.List;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.dao.jpa.objetivoJurisdiccional.ObjetivoJurisdiccionalRepository;
import ar.gob.buenosaires.dao.jpa.objetivoJurisdiccional.ObjetivoJurisdiccionalRepositoryImpl;
import ar.gob.buenosaires.dao.jpa.objetivoOperativo.ObjetivoOperativoJpaDao;
import ar.gob.buenosaires.dao.jpa.objetivoOperativo.ObjetivoOperativoRepository;
import ar.gob.buenosaires.domain.ObjetivoJurisdiccional;
import ar.gob.buenosaires.domain.ObjetivoOperativo;
import ar.gob.buenosaires.esb.exception.CodigoError;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.ObjetivoOperativoService;

@Service
public class ObjetivoOperativoServiceImpl implements ObjetivoOperativoService {

	private static final String WILDCARD = "%";

	@Autowired
	private ObjetivoOperativoRepository repositorio;
	
	@Autowired
	private ObjetivoJurisdiccionalRepository repositorioObjetivoJurisdiccional;

	@Override
	public List<ObjetivoOperativo> getObjetivosOperativos() {
		return getObjetivoOperativoDAO().findAll();
	}

	@Override
	public ObjetivoOperativo getObjetivoOperativoPorNombre(String nombre) {
		return getObjetivoOperativoDAO().findByNombre(nombre);
	}

	@Override
	public ObjetivoOperativo getObjetivoOperativoPorId(Long id) {
		return getObjetivoOperativoDAO().findOne(id);
	}
	
	@Override
	public ObjetivoOperativo getObjetivoOperativoPorCodigo(String codigo) {
		return getObjetivoOperativoDAO().findByCodigo(codigo);
	}
	
	@Override
	public ObjetivoOperativo createObjetivoOperativo(ObjetivoOperativo objetivoOperativo) throws ESBException {
		ObjetivoJurisdiccional objetivoJurisdiccional = repositorioObjetivoJurisdiccional.getObjetivoJurisdiccionalJpaDao()
				.findOne(objetivoOperativo.getIdObjetivoJurisdiccionalAux());
		
		if (objetivoJurisdiccional != null) {
			validarNombre(getCodigoJurisdiccion(objetivoJurisdiccional.getCodigo()), objetivoOperativo.getNombre(), String.valueOf(objetivoOperativo.getIdObjetivoOperativo()));
			objetivoOperativo.setObjetivoJurisdiccional(objetivoJurisdiccional);
			objetivoOperativo.setCodigo(getProximoCodigoObjOperativo(objetivoJurisdiccional));
			return getObjetivoOperativoDAO().save(objetivoOperativo);			
		} else { 
			throw new ESBException(CodigoError.OBJETIVO_ESTRATEGICO_INEXISTENTE.getCodigo(), "El objetivoJurisdiccional con id: "
					+ objetivoOperativo.getIdObjetivoJurisdiccionalAux()
					+ " no existe");
		}
	}

	@Override
	public ObjetivoOperativo updateObjetivoOperativo(ObjetivoOperativo objetivoOperativo) throws ESBException {
		ObjetivoJurisdiccional objetivoJurisdiccional = repositorioObjetivoJurisdiccional.getObjetivoJurisdiccionalJpaDao()
				.findOne(objetivoOperativo.getIdObjetivoJurisdiccionalAux());
		
		if (objetivoJurisdiccional != null) {	
			validarNombre(getCodigoJurisdiccion(objetivoJurisdiccional.getCodigo()), objetivoOperativo.getNombre(), String.valueOf(objetivoOperativo.getIdObjetivoOperativo()));
			objetivoOperativo.setObjetivoJurisdiccional(objetivoJurisdiccional);
			return getObjetivoOperativoDAO().save(objetivoOperativo);
		} else { 
			throw new ESBException(CodigoError.OBJETIVO_ESTRATEGICO_INEXISTENTE.getCodigo(), "El objetivoJurisdiccional con id: " + objetivoOperativo.getIdObjetivoJurisdiccionalAux()
					+ " no existe");
		}
	}
	
	private String getCodigoJurisdiccion(String codigo) {		
		String[] parts = codigo.split("\\.");
		return parts[0];
	}
	
	private void validarNombre(String codigoJuri, String nombre, String id) throws ESBException {		
		String op = getObjetivoOperativoDAO().getObjetivosPorNombreYJurisdccion(codigoJuri + WILDCARD, nombre);
		if (op != null && ! op.equalsIgnoreCase(id)) {
			throw new ESBException(CodigoError.OBJETIVO_OPERATIVO_REPETIDO.getCodigo(), "Ya existe un objetivo operativo con el nombre: '" + nombre + "' para esta Jurisdiccion"); 
		}
	}
	
	@Override
	public void deleteObjetivoOperativo(Long id) throws ESBException {
		ObjetivoOperativo objetivoOperativo = getObjetivoOperativoDAO().findOne(id);
		if (objetivoOperativo != null) {
			getObjetivoOperativoDAO().delete(objetivoOperativo);
		} else {
			throw new ESBException(CodigoError.OBJETIVO_OPERATIVO_INEXISTENTE.getCodigo(), "No se encontro Objetivo Operativo con id: " + id);
		}
	}
	
	private String getProximoCodigoObjOperativo(ObjetivoJurisdiccional objetivoJurisdiccional) {
		Integer codigoObjetivoOperativo = 0;

		if (!objetivoJurisdiccional.getObjetivosOperativos().isEmpty()) {
			codigoObjetivoOperativo = objetivoJurisdiccional.getObjetivosOperativos().parallelStream()
					.map(oj -> Integer.parseInt(oj.getCodigo().split("\\.")[2])).max(Integer::compare).get();
		}
		return objetivoJurisdiccional.getCodigo().concat(".").concat(String.valueOf(codigoObjetivoOperativo + 1));
	}

	ObjetivoOperativoJpaDao getObjetivoOperativoDAO() {
		return repositorio.getObjetivoOperativoJpaDao();
	}
	
	@VisibleForTesting
	public void setObjetivoJurisdiccionalRepository(ObjetivoJurisdiccionalRepositoryImpl repo) {
		this.repositorioObjetivoJurisdiccional = repo;
	}

	@VisibleForTesting
	public void setRepositorioObjetivoOperativo(ObjetivoOperativoRepository repositorioObjetivoOperativo) {
		this.repositorio = repositorioObjetivoOperativo;
	}
}
