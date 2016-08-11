package ar.gob.buenosaires.service;

import java.util.List;

import ar.gob.buenosaires.domain.Rol;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface RolService {

	Rol createRol(Rol rol);

	Rol updateRol(Rol rol);

	void deleteRol(Long id) throws ESBException;

	Rol getRolPorId(Long id);

	List<Rol> getRoles();

}
