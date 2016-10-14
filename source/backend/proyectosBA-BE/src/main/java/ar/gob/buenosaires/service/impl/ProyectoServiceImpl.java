package ar.gob.buenosaires.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.dao.jpa.objetivoOperativo.ObjetivoOperativoRepository;
import ar.gob.buenosaires.dao.jpa.presupuestoPorAnio.PresupuestoPorAnioRepository;
import ar.gob.buenosaires.dao.jpa.proyecto.ProyectoJpaDao;
import ar.gob.buenosaires.dao.jpa.proyecto.ProyectoRepository;
import ar.gob.buenosaires.dao.jpa.proyecto.ProyectoRepositoryImpl;
import ar.gob.buenosaires.domain.EstadoProyecto;
import ar.gob.buenosaires.domain.EtiquetasMsg;
import ar.gob.buenosaires.domain.ObjetivoOperativo;
import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.ProyectoRespMsg;
import ar.gob.buenosaires.esb.exception.CodigoError;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.geocoder.adapter.response.GeoCoderResponse;
import ar.gob.buenosaires.geocoder.service.GeoCoderService;
import ar.gob.buenosaires.geocoder.service.impl.GeoCoderServiceImpl;
import ar.gob.buenosaires.otrasEtiquetas.OtrasEtiquetasRepository;
import ar.gob.buenosaires.service.ProyectoService;

@Service
public class ProyectoServiceImpl extends AbstractSeriviceImpl implements ProyectoService {

	@Autowired
	private ProyectoRepository repositorio;

	@PersistenceContext
	private EntityManager entityManager;

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
	public Proyecto updateProyecto(final Proyecto proyecto, Usuario usuario) throws ESBException {
		final ObjetivoOperativo op = repositorioObjetivoOperativo.getObjetivoOperativoJpaDao()
				.findOne(proyecto.getIdObjetivoOperativo2());

		if (op != null) {
			proyecto.setObjetivoOperativo(op);
			guardarCoordenadas(proyecto);
			actualizarEstado(proyecto, usuario);
			proyecto.setOtrasEtiquetas(otrasEtiquetasRepository.getOtrasEtiquetasJpaDao().save(proyecto.getOtrasEtiquetas()));
			validarPresupuestoAprobado(proyecto);
			validarPrioridadDeJefatura(proyecto);
			return getProyectoDAO().save(proyecto);
		}
		throw new ESBException(CodigoError.OBJETIVO_OPERATIVO_INEXISTENTE.getCodigo(), "El objetivo operativo con id: "
				+ proyecto.getIdObjetivoOperativo2() + " no existe");
	}
	
	private void actualizarEstado(final Proyecto proyecto, Usuario usuario) {
		if (usuario != null && EstadoProyecto.VERIFICADO.getName().equalsIgnoreCase(proyecto.getEstado()) && !usuario.tienePerfilSecretaria()) {
			proyecto.setEstado(EstadoProyecto.PRESENTADO.getName());
			proyecto.setVerificado(false);
		} else {
			proyecto.setEstado(proyecto.getEstadoActualizado());
		}
	}
		
	@Override
	public Proyecto etiquetarProyecto(Long id, EtiquetasMsg etiquetas) throws ESBException {
		final Proyecto proyecto = repositorio.getProyectoJpaDao().findOne(id);
		if (proyecto != null) {
			proyecto.setCompromisosPublicos(etiquetas.getCompromisosPublicos());
			proyecto.setTemasTransversales(etiquetas.getTemasTransversales());
			proyecto.setOtrasEtiquetas(etiquetas.getOtrasEtiquetas());
			return getProyectoDAO().save(proyecto);
		} else {
			throw new ESBException(CodigoError.PROYECTO_INEXISTENTE.getCodigo(), "El Proyecto con id: " + id + " no existe");
		}
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
	
	private void validarPresupuestoAprobado(Proyecto proyecto) throws ESBException {
		String estadoProyecto = proyecto.getEstado();
		List<String> estadosConPresuAprobado = Arrays.asList(
				EstadoProyecto.PREAPROBADO.getName(),
				EstadoProyecto.PREAPROBADO_COMPLETO.getName(),
				EstadoProyecto.APROBADO.getName());

		if (estadosConPresuAprobado.contains(estadoProyecto)) {
			if(proyecto.getTotalPresupuestoAprobado() == null) {
				throw new ESBException(CodigoError.PRESUPUESTO_APROBADO_ERRONEO.getCodigo(),"El presupuesto aprobado es obligatorio");
			}
						
			if (proyecto.getTotalPresupuestoAprobado() > proyecto.getTotalPedido()) {
				throw new ESBException(CodigoError.PRESUPUESTO_APROBADO_ERRONEO.getCodigo(),"El presupuesto aprobado no puede ser mayor al solicitado");
			}
			
		} else {
			proyecto.setTotalPresupuestoAprobado(null);
		}
	}
	
	private void validarPrioridadDeJefatura(Proyecto proyecto) throws ESBException {
		String estadoProyecto = proyecto.getEstado();
		List<String> estadosConPrioridadJefatura = Arrays.asList(
				EstadoProyecto.DEMORADO.getName(),
				EstadoProyecto.PREAPROBADO.getName(),
				EstadoProyecto.RECHAZADO.getName(),
				EstadoProyecto.PREAPROBADO_COMPLETO.getName(),
				EstadoProyecto.APROBADO.getName());
				
		if (estadosConPrioridadJefatura.contains(estadoProyecto)) {
			String prioridadJefatura = proyecto.getPrioridadJefatura();
			if (prioridadJefatura == null || prioridadJefatura.isEmpty() ) {
				throw new ESBException(CodigoError.PRIORIDAD_JEFATURA_INCOMPLETA.getCodigo(),"El estado " + estadoProyecto + " debe contener prioridad de jefatura");
			}
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

	@Override
	@SuppressWarnings("unchecked")
	public Object executeCustomStatement(ESBEvent event, ProyectoRespMsg response, String statement) {
		List resultList = entityManager.createNativeQuery(statement).getResultList();
		response.setCustomStatementResult(resultList);
		event.setObj(response);
		return event;
	}

	@Override
	public List<Proyecto> buscarResumenProyectosPriorizacion() {
		return getProyectoDAO().findResumenProyectosPriorizacion();
	}
}
