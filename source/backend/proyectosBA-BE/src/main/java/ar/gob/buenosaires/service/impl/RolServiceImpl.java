package ar.gob.buenosaires.service.impl;

import java.util.List;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.dao.jpa.rol.RolJpaDao;
import ar.gob.buenosaires.dao.jpa.rol.RolRepository;
import ar.gob.buenosaires.dao.jpa.rol.RolRepositoryImpl;
import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.domain.Rol;
import ar.gob.buenosaires.esb.exception.CodigoError;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.RolService;

@Service
public class RolServiceImpl implements RolService {
	
	@Autowired
	private RolRepository repository;

	@Override
	public Rol createRol(Rol rol) {
		return getRolJpaDao().save(rol);
	}

	@Override
	public Rol updateRol(Rol rol) {
		return getRolJpaDao().save(rol);
	}

	@Override
	public void deleteRol(Long id) throws ESBException {
		Rol rol = getRolJpaDao().findOne(id);
		if (rol != null) {
			getRolJpaDao().delete(rol);
		} else {
			throw new ESBException(CodigoError.ROL_INEXISTENTE.getCodigo(), "No se encontro Rol con id: " + id);
		}		
	}

	@Override
	public Rol getRolPorId(Long id) {
		return getRolJpaDao().getOne(id);
	}

	@Override
	public List<Rol> getRoles() {
		return getRolJpaDao().findAll();
	}
	
	RolJpaDao getRolJpaDao(){
		return repository.getRolJpaDao();
	}
	
	@VisibleForTesting
	public void setRolRepository(RolRepositoryImpl repo) {
		this.repository = repo;
	}
}
