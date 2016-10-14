package ar.gob.buenosaires.esb.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import ar.gob.buenosaires.domain.AccionesProyecto;
import ar.gob.buenosaires.domain.EstadoProyecto;
import ar.gob.buenosaires.domain.ObjetivoOperativo;
import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.ProyectoReqMsg;
import ar.gob.buenosaires.esb.domain.message.ProyectoRespMsg;
import ar.gob.buenosaires.esb.exception.CodigoError;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.util.JMSUtil;
import ar.gob.buenosaires.service.ProyectoService;
import ar.gob.buenosaires.service.UsuarioService;

public class ProyectoHandler extends AbstractBaseEventHandler {

	private final static Logger LOGGER = LoggerFactory.getLogger(ProyectoHandler.class);

	@Autowired
	private ProyectoService service;

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private Environment env;

	@Override
	protected void process(ESBEvent event) throws ESBException {

		logRequestMessage(event, ProyectoService.class);
		final ProyectoReqMsg request = (ProyectoReqMsg) JMSUtil.crearObjeto(getReader(ProyectoReqMsg.class), event.getXml());

		final ProyectoRespMsg response = new ProyectoRespMsg();
		event.setObj(response);
		List<Proyecto> proyectos = new ArrayList<>();
		response.setProyectos(proyectos);
		
		if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE)) {
			retrieveProyectos(response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_CREATE)) {			
			proyectos.add(service.createProyecto(request.getProyecto()));
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_PRESENTAR)) {
			presentarProyecto(event, response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE_ACTIONS)) {
			retrieveActions(event, response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_UPDATE)) {
			updateProyecto(event, response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_ETIQUETAR)) {
			etiquetarProyecto(event, response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_DELETE)) {
			service.deleteProyecto(request.getId());
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_CANCELAR_PRIORIZACION)) {
			service.cancelarPriorizacionDeProyectosNoVerificados();
			service.cancelarPriorizacionDeProyectosVerificados();
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_INICIAR_PRIORIZACION)) {
			service.iniciarPriorizacionDeProyectos();
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE_PRIORIDADES_JEFATURA)) {
			String[] prioridadesJefatura = env.getProperty("proyecto.priorizado.opciones.prioridadJefatura").split(",");
			response.setPrioridadesJefatura(Arrays.asList(prioridadesJefatura));
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_CUSTOM_STATEMENT)) {
			service.executeCustomStatement(event, response, request.getCustomStatement());
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE_RESUMEN_PRIORIZACION)) {
			getResumenProyectosPriorizacion(event, response, request);
		} else {
			throw new ESBException(CodigoError.ACCION_INEXISTENTE.getCodigo(), "La accion: " + event.getAction() + ", no existe para servicio de Proyecto");
		}
		logResponseMessage(event, ProyectoService.class);
	}	

	private void getResumenProyectosPriorizacion(ESBEvent event, ProyectoRespMsg response, ProyectoReqMsg request) {
		List<Proyecto> proyectos = service.buscarResumenProyectosPriorizacion();
		
		int presentado = 0, verificado = 0, enpriorizacion = 0;
		for (Proyecto proyecto : proyectos) {
			if (proyecto.getEstado().equalsIgnoreCase(EstadoProyecto.PRESENTADO.getName())) {
				presentado++;
			} else if (proyecto.getEstado().equalsIgnoreCase(EstadoProyecto.VERIFICADO.getName())) {
				verificado++;
			} else if (proyecto.getEstado().equalsIgnoreCase(EstadoProyecto.ENPRIORIZACION.getName())) {
				enpriorizacion++;
			}
		}
		
		String result = "{ \"" + EstadoProyecto.PRESENTADO.getName() + "\" : \"" + presentado + "\", \""
				+ EstadoProyecto.VERIFICADO.getName() + "\" : \"" + verificado + "\", \"EnPriorizacion\" : \""
				+ enpriorizacion + "\" }";
		
		response.setResumenProyectosPriorizacion(result);
	}

	private void presentarProyecto(ESBEvent event, final ProyectoRespMsg response, final ProyectoReqMsg request) throws ESBException {
		/*
		 * TODO temporal hasta que arreglemos el bus. Sacarlo una vez que el Bus
		 * no nos rompa las relaciones. Sin esto falla el check que se fija si
		 * el proyecto esta completo
		 */
		Proyecto proyecto = request.getProyecto();
		proyecto.setObjetivoOperativo(new ObjetivoOperativo());

		if (EstadoProyecto.COMPLETO.getName().equals(proyecto.getEstadoActualizado())) {
			proyecto.setEstado(EstadoProyecto.PRESENTADO.getName());
			proyecto.setVerificado(false);
			if (proyecto.getIdProyecto() == null) {
				List<Proyecto> proyectos = new ArrayList<Proyecto>();
				proyectos.add(service.createProyecto(proyecto));
				addProyectosToResponse(event, response, proyectos);
			} else {
				updateProyecto(event, response, request);
			}
		} else {
			throw new ESBException(CodigoError.PROYECTO_NO_COMPLETO.getCodigo(),"El proyecto debe estar completo para poder ser presentado");
		}
	}

	private void retrieveProyectos(final ProyectoRespMsg response, final ProyectoReqMsg request) {
		List<Proyecto> proyectos = new ArrayList<>();

		if (request.getId() != null) {
			proyectos.add(service.getProyectoPorId(request.getId()));
		} else if (request.getName() != null) {
			if (request.getIdJurisdiccion() != null) {
				if (request.getEstados() == null || request.getEstados().isEmpty()) {
					proyectos.add(service.getProyectoPorNombreYIdJurisdiccion(request.getName(),
							request.getIdJurisdiccion()));
				} else if (request.getEstados() != null) {
					proyectos.add(service.getProyectoPorNombreYIdJurisdiccionYCiertosEstados(request.getName(),
							request.getIdJurisdiccion(), request.getEstados()));
				}
			} else {
				proyectos.add(service.getProyectoPorNombre(request.getName()));
			}
		} else if (request.getCodigo() != null) {
			proyectos.add(service.getProyectoPorCodigo(request.getCodigo()));
		} else if (request.getEstados() != null && !request.getEstados().isEmpty()) {
			buscarPorVariosEstados(request, proyectos);
		} else {
			proyectos = service.getProyectos();
		}
		response.setProyectos(proyectos);
	}

	private void buscarPorVariosEstados(final ProyectoReqMsg request, List<Proyecto> proyectos) {
		for (String estado : request.getEstados()) {
			proyectos.addAll(service.getProyectosPorEstado(estado));
		}
	}

	private void retrieveActions(ESBEvent event, ProyectoRespMsg response, ProyectoReqMsg request) {
		Proyecto proyectoGuardado = service.getProyectoPorId(request.getId());
		String estado = proyectoGuardado.getEstado();
		List<String> acciones = new ArrayList<>();
		Usuario usuario = usuarioService.getUsuarioPorEmail(request.getUsuario().getEmail());
		
		if (usuario.tienePerfilSecretaria()) {
			getAccionesParaSecretaria(estado, acciones);
		} else {
			getAccionesParaJurisdiccion(estado, acciones);
		}

		response.setAccionesPermitidas(acciones);
		event.setObj(response);
	}

	private void getAccionesParaSecretaria(String estado, List<String> acciones) {
		if (EstadoProyecto.PRESENTADO.getName().equalsIgnoreCase(estado)) {
			getAccionesParaPresentado(acciones);		
		} else if (EstadoProyecto.VERIFICADO.getName().equalsIgnoreCase(estado)) {
			getAccionesParaVerificado(acciones);			
		} else if (EstadoProyecto.PREAPROBADO.getName().equalsIgnoreCase(estado)) {
			getAccionesParaPreAprobado(acciones);		
		} else if (EstadoProyecto.DEMORADO.getName().equalsIgnoreCase(estado)) {
			getAccionesParaDemorado(acciones);			
		} else if (EstadoProyecto.CANCELADO.getName().equalsIgnoreCase(estado)) {
			acciones.add(AccionesProyecto.DESHACER_CANCELACION.getName());		
		} else if (EstadoProyecto.RECHAZADO.getName().equalsIgnoreCase(estado)) {
			acciones.add(AccionesProyecto.DESHACER_RECHAZO.getName());
		}
	}

	private void getAccionesParaDemorado(List<String> acciones) {
		acciones.add(AccionesProyecto.PREAPROBAR.getName());
		acciones.add(AccionesProyecto.RECHAZAR.getName());
		acciones.add(AccionesProyecto.REANUDAR.getName());
	}

	private void getAccionesParaPreAprobado(List<String> acciones) {
		acciones.add(AccionesProyecto.DEMORAR.getName());
		acciones.add(AccionesProyecto.DESHACER_PREAPROBACION.getName());
	}

	private void getAccionesParaVerificado(List<String> acciones) {
		acciones.add(AccionesProyecto.CANCELAR.getName());
		acciones.add(AccionesProyecto.PREAPROBAR.getName());
		acciones.add(AccionesProyecto.DEMORAR.getName());
		acciones.add(AccionesProyecto.RECHAZAR.getName());
	}

	private void getAccionesParaPresentado(List<String> acciones) {
		getAccionesParaVerificado(acciones);				
		acciones.add(AccionesProyecto.VERIFICAR.getName());
	}

	private void getAccionesParaJurisdiccion(String estado, List<String> acciones) {
		if (EstadoProyecto.PRESENTADO.getName().equalsIgnoreCase(estado) || EstadoProyecto.VERIFICADO.getName().equalsIgnoreCase(estado)) {
			acciones.add(AccionesProyecto.CANCELAR.getName());
		} else if (EstadoProyecto.CANCELADO.getName().equalsIgnoreCase(estado)) {
			acciones.add(AccionesProyecto.DESHACER_CANCELACION.getName());
		}
	}

	private void updateProyecto(ESBEvent event, final ProyectoRespMsg response, final ProyectoReqMsg request)
			throws ESBException {
		List<Proyecto> proyectos = new ArrayList<>();				
		Usuario usuario = usuarioService.getUsuarioPorEmail(request.getEmailUsuario());
		request.setUsuario(usuario);
		cambiarEstadoSiCorresponde(request);		
		proyectos.add(service.updateProyecto(request.getProyecto(), request.getUsuario()));

		addProyectosToResponse(event, response, proyectos);
	}
	
	private void etiquetarProyecto(ESBEvent event, ProyectoRespMsg response, ProyectoReqMsg request) throws ESBException {
		List<Proyecto> proyectos = new ArrayList<>();
		proyectos.add(service.etiquetarProyecto(request.getId(),request.getEtiquetas()));

		addProyectosToResponse(event, response, proyectos);
	}
	
	private void cambiarEstadoSiCorresponde(final ProyectoReqMsg request) throws ESBException {
		String accion = request.getAccion();
	
		if (accion != null && ! accion.isEmpty()){
			if (AccionesProyecto.CANCELAR.getName().equalsIgnoreCase(accion)) {
				validarCancelarProyecto(request);
			} else if (AccionesProyecto.DESHACER_CANCELACION.getNameSinEspacios().equalsIgnoreCase(accion)) {
				validarDeshacerCancelacion(request);
			} else if (AccionesProyecto.VERIFICAR.getNameSinEspacios().equalsIgnoreCase(accion)) { 
				validarVerificarProyecto(request);
			} else if (AccionesProyecto.PREAPROBAR.getNameSinEspacios().equalsIgnoreCase(accion)) {
				validarPreAprobarProyecto(request);
			} else if (AccionesProyecto.DEMORAR.getNameSinEspacios().equalsIgnoreCase(accion)) {
				validarDemorarProyecto(request);
			} else if (AccionesProyecto.RECHAZAR.getNameSinEspacios().equalsIgnoreCase(accion)) {
				validarRechazarProyecto(request);
			} else if (AccionesProyecto.DESHACER_PREAPROBACION.getNameSinEspacios().equalsIgnoreCase(accion)) {
				validarDeshacerPreAprobacionProyecto(request);
			} else if (AccionesProyecto.REANUDAR.getNameSinEspacios().equalsIgnoreCase(accion)) {
				validarReanudarProyecto(request);
			} else if (AccionesProyecto.DESHACER_RECHAZO.getNameSinEspacios().equalsIgnoreCase(accion)) {
				validarDeshacerRechazoProyecto(request);
			}
		}
	}

	private void validarCancelarProyecto(final ProyectoReqMsg request) throws ESBException {
		Proyecto proyectoGuardado = service.getProyectoPorId(request.getProyecto().getIdProyecto());

		if (EstadoProyecto.PRESENTADO.getName().equalsIgnoreCase(proyectoGuardado.getEstado())
				|| EstadoProyecto.VERIFICADO.getName().equalsIgnoreCase(proyectoGuardado.getEstado())) {
			request.getProyecto().setEstado(EstadoProyecto.CANCELADO.getName());
		} else {
			throw new ESBException(CodigoError.PROYECTO_NO_PRESENTADO_O_VERIFICADO.getCodigo(), "El proyecto debe estar presentado o verificado para poder ser cancelado");
		}
	}

	private void validarVerificarProyecto(ProyectoReqMsg request)
			throws ESBException {
		Proyecto proyectoGuardado = service.getProyectoPorId(request.getProyecto().getIdProyecto());

		if (EstadoProyecto.PRESENTADO.getName().equalsIgnoreCase(proyectoGuardado.getEstado())) {
			request.getProyecto().setEstado(EstadoProyecto.VERIFICADO.getName());
			request.getProyecto().setVerificado(true);
		} else {
			throw new ESBException(CodigoError.PROYECTO_NO_PRESENTADO.getCodigo(), "El proyecto debe estar presentado para poder ser verificado");
		}
	}
	
	private void validarDeshacerCancelacion(ProyectoReqMsg request)
			throws ESBException {
		Proyecto proyectoGuardado = service.getProyectoPorId(request.getProyecto().getIdProyecto());

		if (EstadoProyecto.CANCELADO.getName().equalsIgnoreCase(proyectoGuardado.getEstado())) {
			setEstadoAnterior(request.getProyecto());
		} else {
			throw new ESBException(CodigoError.PROYECTO_NO_CANCELADO.getCodigo(), "El proyecto debe estar cancelado para deshacer la cancelacion");
		}
	}

	private void validarPreAprobarProyecto(ProyectoReqMsg request) throws ESBException {
		Proyecto proyectoGuardado = service.getProyectoPorId(request.getProyecto().getIdProyecto());
		
		if (EstadoProyecto.PRESENTADO.getName().equalsIgnoreCase(proyectoGuardado.getEstado()) ||
			EstadoProyecto.VERIFICADO.getName().equalsIgnoreCase(proyectoGuardado.getEstado()) ||
			EstadoProyecto.DEMORADO.getName().equalsIgnoreCase(proyectoGuardado.getEstado()) ){			
			
			request.getProyecto().setEstado(EstadoProyecto.PREAPROBADO.getName());
			
		} else {
			throw new ESBException(CodigoError.ESTADO_INVALIDO.getCodigo(), "El proyecto debe estar presentado/verificado/aplazado para ser pre aprobado");
		}
	}
	
	private void validarDemorarProyecto(ProyectoReqMsg request) throws ESBException {
		Proyecto proyectoGuardado = service.getProyectoPorId(request.getProyecto().getIdProyecto());
		
		if (EstadoProyecto.PRESENTADO.getName().equalsIgnoreCase(proyectoGuardado.getEstado()) ||
			EstadoProyecto.VERIFICADO.getName().equalsIgnoreCase(proyectoGuardado.getEstado()) ||
			EstadoProyecto.PREAPROBADO.getName().equalsIgnoreCase(proyectoGuardado.getEstado()) ){			
			
			request.getProyecto().setEstado(EstadoProyecto.DEMORADO.getName());
			
		} else {
			throw new ESBException(CodigoError.ESTADO_INVALIDO.getCodigo(), "El proyecto debe estar presentado/verificado/pre-aprobado para ser demorado");
		}
	}
	
	private void validarRechazarProyecto(ProyectoReqMsg request) throws ESBException {
		Proyecto proyectoGuardado = service.getProyectoPorId(request.getProyecto().getIdProyecto());
		
		if (EstadoProyecto.PRESENTADO.getName().equalsIgnoreCase(proyectoGuardado.getEstado()) ||
				EstadoProyecto.VERIFICADO.getName().equalsIgnoreCase(proyectoGuardado.getEstado()) ||
				EstadoProyecto.DEMORADO.getName().equalsIgnoreCase(proyectoGuardado.getEstado()) ){			
			
			request.getProyecto().setEstado(EstadoProyecto.RECHAZADO.getName());
			
		} else {
			throw new ESBException(CodigoError.ESTADO_INVALIDO.getCodigo(), "El proyecto debe estar presentado/verificado/aplazado para ser demorado");
		}
	}
	
	private void validarDeshacerPreAprobacionProyecto(ProyectoReqMsg request) throws ESBException {
		Proyecto proyectoGuardado = service.getProyectoPorId(request.getProyecto().getIdProyecto());
		
		if (EstadoProyecto.PREAPROBADO.getName().equalsIgnoreCase(proyectoGuardado.getEstado()) ){			
			
			setEstadoAnterior(request.getProyecto());
			
		} else {
			throw new ESBException(CodigoError.ESTADO_INVALIDO.getCodigo(), "El proyecto debe estar pre aprobado");
		}
	}

	private void validarReanudarProyecto(ProyectoReqMsg request) throws ESBException {
		Proyecto proyectoGuardado = service.getProyectoPorId(request.getProyecto().getIdProyecto());
		
		if (EstadoProyecto.DEMORADO.getName().equalsIgnoreCase(proyectoGuardado.getEstado()) ){			
			
			setEstadoAnterior(request.getProyecto());
			
		} else {
			throw new ESBException(CodigoError.ESTADO_INVALIDO.getCodigo(), "El proyecto debe estar demorado");
		}
	}
	
	private void validarDeshacerRechazoProyecto(ProyectoReqMsg request) throws ESBException {
		Proyecto proyectoGuardado = service.getProyectoPorId(request.getProyecto().getIdProyecto());
		
		if (EstadoProyecto.RECHAZADO.getName().equalsIgnoreCase(proyectoGuardado.getEstado()) ){						
			setEstadoAnterior(request.getProyecto());			
		} else {
			throw new ESBException(CodigoError.ESTADO_INVALIDO.getCodigo(), "El proyecto debe estar rechazado");
		}
	}
	
	private void setEstadoAnterior(Proyecto proyecto) {
		if (proyecto.getVerificado()) {
			proyecto.setEstado(EstadoProyecto.VERIFICADO.getName());
		} else {
			proyecto.setEstado(EstadoProyecto.PRESENTADO.getName());
		}
	}

	private void addProyectosToResponse(ESBEvent event, final ProyectoRespMsg response, List<Proyecto> proyectos) {
		response.setProyectos(proyectos);
		event.setObj(response);
	}

	@Override
	protected boolean validateEvent(ESBEvent event) {
		return event.getType().equalsIgnoreCase(ProyectoReqMsg.PROYECTO_TYPE);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
