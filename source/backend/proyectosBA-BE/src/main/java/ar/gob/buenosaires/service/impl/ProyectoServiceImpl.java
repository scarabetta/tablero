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
import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.esb.exception.CodigoError;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.geocoder.adapter.response.GeoCoderResponse;
import ar.gob.buenosaires.geocoder.service.GeoCoderService;
import ar.gob.buenosaires.geocoder.service.impl.GeoCoderServiceImpl;
import ar.gob.buenosaires.otrasEtiquetas.OtrasEtiquetasRepository;
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
	private OtrasEtiquetasRepository otrasEtiquetasRepository;

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
			proyecto.setObjetivoOperativo(op);
			guardarCoordenadas(proyecto);
			proyecto.setEstado(proyecto.getEstadoActualizado());
			proyecto.setOtrasEtiquetas(otrasEtiquetasRepository.getOtrasEtiquetasJpaDao().save(proyecto.getOtrasEtiquetas()));
			return getProyectoDAO().save(proyecto);
		} else {
			throw new ESBException(CodigoError.OBJETIVO_OPERATIVO_INEXISTENTE.getCodigo(), "El objetivo operativo con id: "
					+ proyecto.getIdObjetivoOperativo2() + " no existe");
		}
	}

	@Override
	public Proyecto updateProyecto(final Proyecto proyecto) throws ESBException {
		final ObjetivoOperativo op = repositorioObjetivoOperativo.getObjetivoOperativoJpaDao()
				.findOne(proyecto.getIdObjetivoOperativo2());

		if (op != null) {
			proyecto.setObjetivoOperativo(op);
			guardarCoordenadas(proyecto);
			proyecto.setEstado(proyecto.getEstadoActualizado());
			proyecto.setOtrasEtiquetas(otrasEtiquetasRepository.getOtrasEtiquetasJpaDao().save(proyecto.getOtrasEtiquetas()));
			return getProyectoDAO().save(proyecto);
		}
		throw new ESBException(CodigoError.OBJETIVO_OPERATIVO_INEXISTENTE.getCodigo(), "El objetivo operativo con id: "
				+ proyecto.getObjetivoOperativo().getIdObjetivoOperativo() + "no existe");
	}

	@Override
	public void deleteProyecto(final Long id) throws ESBException {
		final Proyecto proyecto = getProyectoDAO().findOne(id);
		if (proyecto != null) {
			getProyectoDAO().delete(proyecto);
		} else {
			throw new ESBException(CodigoError.PROYECTO_INEXISTENTE.getCodigo(), "No se encontro proyecto con id: " + id);
		}
	}

	ProyectoJpaDao getProyectoDAO() {
		return repositorio.getProyectoJpaDao();
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

	@VisibleForTesting
	public void setRepositorioOtrasEtiquetas(final OtrasEtiquetasRepository otrasEtiquetasRepository) {
		this.otrasEtiquetasRepository = otrasEtiquetasRepository;
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

	@Override
	public Proyecto getProyectoPorNombreYIdJurisdiccionYCiertosEstados(String nombre, Long IdJurisdiccion, List<String> estados) {
		return getProyectoDAO().findByNombreAndIdJurisdiccionAndCiertosEstados(nombre, IdJurisdiccion, estados);
	}

	@Override
	public Proyecto getProyectoPorNombreYIdJurisdiccion(String nombre, Long IdJurisdiccion) {
		return getProyectoDAO().findByNombreAndIdJurisdiccion(nombre, IdJurisdiccion);
	}
}
