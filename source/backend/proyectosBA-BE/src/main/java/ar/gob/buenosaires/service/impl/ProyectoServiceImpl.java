package ar.gob.buenosaires.service.impl;

import java.util.List;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.dao.jpa.objetivoOperativo.ObjetivoOperativoRepository;
import ar.gob.buenosaires.dao.jpa.presupuestoPorAnio.PresupuestoPorAnioRepository;
import ar.gob.buenosaires.dao.jpa.proyecto.ProyectoJpaDao;
import ar.gob.buenosaires.dao.jpa.proyecto.ProyectoRepository;
import ar.gob.buenosaires.dao.jpa.proyecto.ProyectoRepositoryImpl;
import ar.gob.buenosaires.domain.ObjetivoOperativo;
import ar.gob.buenosaires.domain.PresupuestoPorAnio;
import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.ProyectoService;

@Service
public class ProyectoServiceImpl implements ProyectoService {

	@Autowired
	private ProyectoRepository repositorio;
	
	@Autowired
	private ObjetivoOperativoRepository repositorioObjetivoOperativo;
	
	@Autowired
	private PresupuestoPorAnioRepository repositorioPresupuestoPorAnio;

	@Override
	public List<Proyecto> getProyectos() {
		return getProyectoDAO().findAll();
	}

	@Override
	public Proyecto getProyectoPorNombre(String nombre) {
		return getProyectoDAO().findByNombre(nombre);
	}

	@Override
	public Proyecto getProyectoPorId(Long id) {
		return getProyectoDAO().findOne(id);
	}
	
	@Override
	public Proyecto getProyectoPorCodigo(String codigo) {
		return getProyectoDAO().findByCodigo(codigo);
	}
	
	@Override
	public Proyecto createProyecto(Proyecto proyecto) throws ESBException {
		ObjetivoOperativo op = repositorioObjetivoOperativo.getObjetivoOperativoJpaDao().findOne(proyecto.getIdObjetivoOperativo2());
		if (op != null) {			
			proyecto.setObjetivoOperativo(op);
			Proyecto proyectoGuardado = getProyectoDAO().save(proyecto);			
			updatePresupuestosPorAnio(proyecto);
			return proyectoGuardado;
			
		} else { 
			throw new ESBException("El objetivo operativo con id: "
					+ proyecto.getObjetivoOperativo().getIdObjetivoOperativo()
					+ "no existe");
		}	
	}		

	@Override
	public Proyecto updateProyecto(Proyecto proyecto) throws ESBException {
		ObjetivoOperativo op = repositorioObjetivoOperativo.getObjetivoOperativoJpaDao().findOne(proyecto.getIdObjetivoOperativo2());
		if (op != null) {
			proyecto.setObjetivoOperativo(op);
			Proyecto proyectoGuardado = getProyectoDAO().save(proyecto);
			if (proyectoGuardado != null) {
				deletePresupuestosPorAnioAnteriores(proyecto);
				updatePresupuestosPorAnio(proyectoGuardado);
				return proyectoGuardado;
			}
		}  
		throw new ESBException("El objetivo operativo con id: " + proyecto.getObjetivoOperativo().getIdObjetivoOperativo() + "no existe");
		
		
	}
	
	@Override
	public void deleteProyecto(Long id) throws ESBException {
		Proyecto proyecto = getProyectoDAO().findOne(id);
		if (proyecto != null) {
			getProyectoDAO().delete(proyecto);
		} else {
			throw new ESBException("No se encontro proyecto con id: " + id);
		}
	}

	ProyectoJpaDao getProyectoDAO() {
		return repositorio.getProyectoJpaDao();
	}
	
	private void updatePresupuestosPorAnio(Proyecto proyecto) {
		if(proyecto != null){ 
			List<PresupuestoPorAnio> presupuestos = proyecto.getPresupuestosPorAnio();
			for(PresupuestoPorAnio presupuesto: presupuestos) {
				presupuesto.setProyecto(proyecto);
				repositorioPresupuestoPorAnio.getPresupuestoPorAnioJpaDao().save(presupuesto);			
			}
		}		
	}
	
	private void deletePresupuestosPorAnioAnteriores(Proyecto proyecto) {
		List<PresupuestoPorAnio> presupuestos = repositorioPresupuestoPorAnio.getPresupuestoPorAnioJpaDao().findByProyectoPresupuestoPorAnio(proyecto);
		for (PresupuestoPorAnio presupuesto : presupuestos) {
			repositorioPresupuestoPorAnio.getPresupuestoPorAnioJpaDao().delete(presupuesto);
		}

	}
	
	@VisibleForTesting
	public void setProyectoRepository(ProyectoRepositoryImpl repo) {
		this.repositorio = repo;
	}
	
	@VisibleForTesting
	public void setRepositorioObjetivoOperativo(ObjetivoOperativoRepository repositorioObjetivoOperativo) {
		this.repositorioObjetivoOperativo = repositorioObjetivoOperativo;
	}
	
	@VisibleForTesting
	public void setRepositorioPresupuestoPorAnio(PresupuestoPorAnioRepository presupuestoPorAnioRepository) {
		this.repositorioPresupuestoPorAnio = presupuestoPorAnioRepository;
	}
}
