package ar.gob.buenosaires.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.dao.jpa.errorDescripcion.ErrorDescripcionJpaDao;
import ar.gob.buenosaires.dao.jpa.errorDescripcion.ErrorDescripcionRepository;
import ar.gob.buenosaires.domain.ErrorDescripcion;
import ar.gob.buenosaires.service.ErrorDescripcionService;

@Service
public class ErrorDescripcionServiceImpl implements ErrorDescripcionService {
	
	@Autowired
	private ErrorDescripcionRepository repositoryErrorDescripcion;
	
	@Override
	public List<ErrorDescripcion> getErrores() {		
		return getErrorDescripcionJpaDao().findAll();		
	}
	
	ErrorDescripcionJpaDao getErrorDescripcionJpaDao(){
		return repositoryErrorDescripcion.getErrorDescripcionJpaDao();
	}
}
