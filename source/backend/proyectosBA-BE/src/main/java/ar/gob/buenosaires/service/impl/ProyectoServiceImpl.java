package ar.gob.buenosaires.service.impl;

import java.util.List;
import java.util.function.Consumer;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.dao.jpa.objetivoOperativo.ObjetivoOperativoRepository;
import ar.gob.buenosaires.dao.jpa.presupuestoPorAnio.PresupuestoPorAnioRepository;
import ar.gob.buenosaires.dao.jpa.proyecto.ProyectoJpaDao;
import ar.gob.buenosaires.dao.jpa.proyecto.ProyectoRepository;
import ar.gob.buenosaires.dao.jpa.proyecto.ProyectoRepositoryImpl;
import ar.gob.buenosaires.domain.ArchivoProyecto;
import ar.gob.buenosaires.domain.ObjetivoOperativo;
import ar.gob.buenosaires.domain.PresupuestoPorAnio;
import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.geocoder.adapter.response.GeoCoderResponse;
import ar.gob.buenosaires.geocoder.service.GeoCoderService;
import ar.gob.buenosaires.geocoder.service.impl.GeoCoderServiceImpl;
import ar.gob.buenosaires.service.ProyectoService;

@Service
public class ProyectoServiceImpl implements ProyectoService {

	@Autowired
	private ProyectoRepository repositorio;

	@Autowired
	private ObjetivoOperativoRepository repositorioObjetivoOperativo;

	@Autowired
	private PresupuestoPorAnioRepository repositorioPresupuestoPorAnio;

	@Autowired
	private GeoCoderService geoCoderService;

	@Override
	public List<Proyecto> getProyectos() {
		return getProyectoDAO().findAll();
	}

	@Override
	public Proyecto getProyectoPorNombre(final String nombre) {
		return getProyectoDAO().findByNombre(nombre);
	}

	@Override
	public Proyecto getProyectoPorId(final Long id) {
		return getProyectoDAO().findOne(id);
	}

	@Override
	public Proyecto getProyectoPorCodigo(final String codigo) {
		return getProyectoDAO().findByCodigo(codigo);
	}

	@Override
	public Proyecto createProyecto(final Proyecto proyecto) throws ESBException {
		final ObjetivoOperativo op = repositorioObjetivoOperativo.getObjetivoOperativoJpaDao()
				.findOne(proyecto.getIdObjetivoOperativo2());
		if (op != null) {
			relacionarProyectoAlArchivos(proyecto);
			relacionarPresupuestoAlProyecto(proyecto);
			proyecto.setObjetivoOperativo(op);
			proyecto.setEstado(proyecto.getEstadoActualizado());
			guardarCoordenadas(proyecto);

			return getProyectoDAO().save(proyecto);
			
		} else {
			throw new ESBException("El objetivo operativo con id: "
					+ proyecto.getObjetivoOperativo().getIdObjetivoOperativo() + "no existe");
		}
	}

	@Override
	public Proyecto updateProyecto(final Proyecto proyecto) throws ESBException {
		final ObjetivoOperativo op = repositorioObjetivoOperativo.getObjetivoOperativoJpaDao()
				.findOne(proyecto.getIdObjetivoOperativo2());
	
		deletePresupuestosPorAnioAnteriores(proyecto);
		relacionarProyectoAlArchivos(proyecto);
		relacionarPresupuestoAlProyecto(proyecto);

		if (op != null) {
			proyecto.setObjetivoOperativo(op);
			proyecto.setEstado(proyecto.getEstadoActualizado());
			guardarCoordenadas(proyecto);

			return getProyectoDAO().save(proyecto);
		}
		throw new ESBException("El objetivo operativo con id: "
				+ proyecto.getObjetivoOperativo().getIdObjetivoOperativo() + "no existe");

	}

	@Override
	public void deleteProyecto(final Long id) throws ESBException {
		final Proyecto proyecto = getProyectoDAO().findOne(id);
		if (proyecto != null) {
			getProyectoDAO().delete(proyecto);
		} else {
			throw new ESBException("No se encontro proyecto con id: " + id);
		}
	}

	ProyectoJpaDao getProyectoDAO() {
		return repositorio.getProyectoJpaDao();
	}

	private void deletePresupuestosPorAnioAnteriores(final Proyecto proyecto) {
		final List<PresupuestoPorAnio> presupuestos = repositorioPresupuestoPorAnio.getPresupuestoPorAnioJpaDao()
				.findByProyectoPresupuestoPorAnio(proyecto);
		for (final PresupuestoPorAnio presupuesto : presupuestos) {
			repositorioPresupuestoPorAnio.getPresupuestoPorAnioJpaDao().delete(presupuesto);
		}
	}

	private void guardarCoordenadas(final Proyecto proyecto) {
		final GeoCoderResponse response = geoCoderService.getGeoCoding(proyecto.getDireccion());
		if (response != null && response.getDireccionesNormalizadas().size() == 1) {
			proyecto.setCoordenadaX(response.getDireccionesNormalizadas().get(0).getCoordenadas().getX());
			proyecto.setCoordenadaY(response.getDireccionesNormalizadas().get(0).getCoordenadas().getY());
		}

	}

	@VisibleForTesting
	public void setProyectoRepository(final ProyectoRepositoryImpl repo) {
		repositorio = repo;
	}

	public void setRepositorioObjetivoOperativo(final ObjetivoOperativoRepository repositorioObjetivoOperativo) {
		this.repositorioObjetivoOperativo = repositorioObjetivoOperativo;
	}

	@VisibleForTesting
	public void setRepositorioPresupuestoPorAnio(final PresupuestoPorAnioRepository presupuestoPorAnioRepository) {
		repositorioPresupuestoPorAnio = presupuestoPorAnioRepository;
	}

	private void relacionarProyectoAlArchivos(final Proyecto proyecto) {
		proyecto.getArchivos().forEach(new Consumer<ArchivoProyecto>() {

			@Override
			public void accept(final ArchivoProyecto t) {
				t.setProyecto(proyecto);
			}
		});
	}

	private void relacionarPresupuestoAlProyecto(final Proyecto proyecto) {
		proyecto.getPresupuestosPorAnio().forEach(new Consumer<PresupuestoPorAnio>() {

			@Override
			public void accept(final PresupuestoPorAnio t) {
				t.setProyecto(proyecto);
			}
		});
	}

	@VisibleForTesting
	public void setGeoCoderService(final GeoCoderServiceImpl geoCoderServiceImpl) {
		geoCoderService = geoCoderServiceImpl;
	}

	@Override
	public List<Proyecto> getProyectosPorEstado(String estado) {
		return getProyectoDAO().findByEstado(estado);
	}

	@Override
	public void cancelarPriorizacionDeProyectosNoVerificados() {
		getProyectoDAO().cancelarPriorizacionNoVerificado();
	}

	@Override
	public void cancelarPriorizacionDeProyectosVerificados() {
		getProyectoDAO().cancelarPriorizacionVerificado();
	}

	@Override
	public void iniciarPriorizacionDeProyectos() {
		getProyectoDAO().iniciarPriorizacionDeProyectos();
	}
}
