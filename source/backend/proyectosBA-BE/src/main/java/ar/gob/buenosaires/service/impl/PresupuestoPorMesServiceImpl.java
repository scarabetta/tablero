package ar.gob.buenosaires.service.impl;

import java.util.List;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.dao.jpa.presupuestoPorMes.PresupuestoPorMesJpaDao;
import ar.gob.buenosaires.dao.jpa.presupuestoPorMes.PresupuestoPorMesRepository;
import ar.gob.buenosaires.dao.jpa.presupuestoPorMes.PresupuestoPorMesRepositoryImpl;
import ar.gob.buenosaires.dao.jpa.proyecto.ProyectoRepository;
import ar.gob.buenosaires.domain.PresupuestoPorMes;
import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.esb.exception.CodigoError;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.PresupuestoPorMesService;

@Service
public class PresupuestoPorMesServiceImpl implements PresupuestoPorMesService {
	
	@Autowired
	private PresupuestoPorMesRepository repository;
	
	@Autowired
	private ProyectoRepository proyectoRepo;

	@Override
	public PresupuestoPorMes getPresupuestoPorMesPorId(Long id) {
		return getPresupuestoPorMesJpaDao().findOne(id);
	}

	@Override
	public List<PresupuestoPorMes> getPresupuestosPorMes() {
		return getPresupuestoPorMesJpaDao().findAll();
	}

	@Override
	public PresupuestoPorMes createPrespuestoPorMes(PresupuestoPorMes ppm) throws ESBException {
		if(ppm.getIdProyectoAux() != null){
			Proyecto proyecto = proyectoRepo.getProyectoJpaDao().findOne(ppm.getIdProyectoAux());
			if(proyecto != null){
				ppm.setProyecto(proyecto);
				return getPresupuestoPorMesJpaDao().save(ppm);
			} else {
				throw new ESBException(CodigoError.PROYECTO_INEXISTENTE.getCodigo(), "El proyecto con el id: "
						+ ppm.getIdProyectoAux() + " no existe");
			}
		} else {
			throw new ESBException(CodigoError.PROYECTO_INEXISTENTE.getCodigo(), "Falta el ID del proyecto al cual pertenece al presupuesto por mes.");
		}
	}

	@Override
	public PresupuestoPorMes updatePrespuestoPorMes(PresupuestoPorMes presupuestoPorMes) {
		return getPresupuestoPorMesJpaDao().save(presupuestoPorMes);
	}

	@Override
	public void deletePresupuestoPorMes(Long id) throws ESBException {
		final PresupuestoPorMes proyecto = getPresupuestoPorMesJpaDao().findOne(id);
		if (proyecto != null) {
			getPresupuestoPorMesJpaDao().delete(proyecto);
		} else {
			throw new ESBException(CodigoError.PRESUPUESTO_POR_MES_INEXISTENTE.getCodigo(), "No se encontro Presupuesto por Mes con id: " + id);
		}
	}

	PresupuestoPorMesJpaDao getPresupuestoPorMesJpaDao(){
		return repository.getPresupuestoPorMesJpaDao();
	}
	
	@VisibleForTesting
	public void setPresupuestoPorMesRepository(PresupuestoPorMesRepositoryImpl repo) {
		this.repository = repo;
	}
}
