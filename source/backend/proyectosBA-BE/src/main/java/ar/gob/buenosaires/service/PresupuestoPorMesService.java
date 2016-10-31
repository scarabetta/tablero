package ar.gob.buenosaires.service;

import java.util.List;

import ar.gob.buenosaires.domain.PresupuestoPorMes;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface PresupuestoPorMesService {

	PresupuestoPorMes getPresupuestoPorMesPorId(Long id);

	List<PresupuestoPorMes> getPresupuestosPorMes();

	PresupuestoPorMes createPrespuestoPorMes(PresupuestoPorMes presupuestoPorMes) throws ESBException;

	PresupuestoPorMes updatePrespuestoPorMes(PresupuestoPorMes presupuestoPorMes);

	void deletePresupuestoPorMes(Long id) throws ESBException;

}
