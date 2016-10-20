package ar.gob.buenosaires.importador.proyecto.priorizado;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.jms.JMSException;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.Predicate;

import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.domain.TemaTransversal;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.IServiceFactory;
import ar.gob.buenosaires.service.TemaTransversalService;

public class ProyectoPriorizadoBuilder {

	List<String> nombreTemasTransversal = new ArrayList<>();

	Double presuAprobadoTotal = new Double(0);

	String prioridadJefatura;

	String estadoAprobacion;

	// fatory de servicios
	private IServiceFactory serviceFactory;

	private Integer idProyecto;

	private List<String> nombresTemaTransversalAsignados;

	public ProyectoPriorizadoBuilder(IServiceFactory serviceFactory) {
		this.serviceFactory = serviceFactory;
	}

	/**
	 * @param nombreTemasTransversal
	 *            the nombreTemasTransversal to set
	 */
	public final void setNombreTemasTransversal(List<String> nombreTemasTransversal) {
		this.nombreTemasTransversal = nombreTemasTransversal;
	}

	public void setNombreTemasTransversalAsignados(List<String> nombresTemaTransversalAsignados) {
		this.nombresTemaTransversalAsignados = nombresTemaTransversalAsignados;

	}

	/**
	 * @param presuAprobadoTotal
	 *            the presuAprobadoTotal to set
	 */
	public final void setPresuAprobadoTotal(Double presuAprobadoTotal) {
		this.presuAprobadoTotal = presuAprobadoTotal;
	}

	/**
	 * @param prioridadJefatura
	 *            the prioridadJefatura to set
	 */
	public final void setPrioridadJefatura(String prioridadJefatura) {
		this.prioridadJefatura = prioridadJefatura;
	}

	/**
	 * @param estadoAprobacion
	 *            the estadoAprobacion to set
	 */
	public final void setEstadoAprobacion(String estadoAprobacion) {
		this.estadoAprobacion = estadoAprobacion;
	}

	public Proyecto build(String email) throws ESBException, JMSException {
		Proyecto proyecto = serviceFactory.getProyectoService().getProyectoPorId(idProyecto.toString());
		proyecto.setEstado(estadoAprobacion);
		proyecto.setTotalPresupuestoAprobado(presuAprobadoTotal);
		proyecto.setPrioridadJefatura(prioridadJefatura);

		asiganarTemasTransversales(proyecto);
		return serviceFactory.getProyectoService().updateProyecto(proyecto, email);
	}

	public void setIdProyecto(Integer idProyecto) {
		this.idProyecto = idProyecto;
	}

	private void asiganarTemasTransversales(Proyecto proyecto) {
		//Limpiamos los TT actuales asociados al Proyecto.
		proyecto.getTemasTransversales().clear();
		if (!nombreTemasTransversal.isEmpty() && !nombresTemaTransversalAsignados.isEmpty()) {
			try {
				TemaTransversalService tTservice = serviceFactory.getTemaTransversalService();
				tTservice.getTemasTransversales().forEach(new Consumer<TemaTransversal>() {

					@Override
					public void accept(TemaTransversal t) {
						if (nombreTemasTransversal.contains(t.getTemaTransversal())
								&& ListUtils.select(proyecto.getTemasTransversales(),
										new PredicadoComparadorTemaTransversal(t)).isEmpty()
								&& nombresTemaTransversalAsignados.contains(t.getTemaTransversal())) {
							proyecto.getTemasTransversales().add(t);
						}
					}
				});
			} catch (ESBException | JMSException e) {
				e.printStackTrace();
			}

		}
	}

	// Inner class para el predicado de filtro de temas transversales
	private final class PredicadoComparadorTemaTransversal implements Predicate<TemaTransversal> {
		private TemaTransversal t;

		public PredicadoComparadorTemaTransversal(TemaTransversal t) {
			this.t = t;

		}

		@Override
		public boolean evaluate(TemaTransversal tt) {
			return tt.getIdTemaTransversal() == t.getIdTemaTransversal();
		}
	}

}
