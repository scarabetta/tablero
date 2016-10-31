package ar.gob.buenosaires.service;

import java.util.List;

import javax.jms.JMSException;

import com.fasterxml.jackson.databind.JsonNode;

import ar.gob.buenosaires.domain.EtiquetasMsg;
import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface ProyectoService {

	List<Proyecto> getProyectos() throws ESBException, JMSException;

	Proyecto getProyectoPorId(String id) throws ESBException, JMSException;

	Proyecto getProyectoPorCodigo(String codigo) throws ESBException, JMSException;

	Proyecto getProyectoByName(String nombre) throws ESBException, JMSException;

	/**
	 * Se filtran por los estados \'Completo\', \'Incompleto\', \'Presentado\')
	 * el SQL es:<br>
	 *
	 * <p>@Query("SELECT p FROM Proyecto p JOIN p.objetivoOperativo op"
			+ " join op.objetivoJurisdiccional oj join oj.jurisdiccion j"
			+ " where j.idJurisdiccion = :idJurisdiccion and p.nombre = :nombre and p.estado in (\'Completo\', \'Incompleto\', \'Presentado\')")</p>
	 * */
	Proyecto getProyectoPorNombreIdJurisdiccionYCiertosEstados(String nombre, Long IdJurisdiccion, List<String> estados) throws ESBException, JMSException;

	/**
	 * Se filtran por los estados \'Completo\', \'Incompleto\', \'Presentado\')
	 * el SQL es:<br>
	 *
	 * <p>@Query("SELECT p FROM Proyecto p JOIN p.objetivoOperativo op"
			+ " join op.objetivoJurisdiccional oj join oj.jurisdiccion j"
			+ " where j.idJurisdiccion = :idJurisdiccion and p.nombre = :nombre")</p>
	 * */
	Proyecto getProyectoPorNombreIdJurisdiccion(String nombre, Long IdJurisdiccion) throws ESBException, JMSException;

	Proyecto createProyecto(Proyecto proyecto, String email) throws ESBException, JMSException;

	Proyecto updateProyecto(Proyecto proyecto, String email) throws ESBException, JMSException;

	void deleteProyecto(String id, String email) throws ESBException, JMSException;

	Proyecto presentarProyecto(Proyecto proyecto, String email) throws ESBException, JMSException;

	Proyecto cambiarEstadoProyecto(Proyecto proyecto, String action, String email) throws ESBException, JMSException;

	List<String> getAccionesPermitidas(String idProyecto, String userMail) throws ESBException, JMSException;

	JsonNode getResumenProyectosPriorizacion() throws ESBException, JMSException;

	void cancelarPriorizacionDeProyectos(String email) throws ESBException, JMSException;

	void updatePriorizarProyectos(String email) throws ESBException, JMSException;

	List<Integer> getTodosLosIdsProyectosEnPriorizacion(String userMail) throws ESBException, JMSException;

	Proyecto etiquetarProyecto(EtiquetasMsg etiquetas, String id, String string) throws ESBException, JMSException;

	List<String> getPrioridadesJefatura() throws ESBException, JMSException;

	Proyecto presentarDetalleProyecto(Proyecto proyecto, String mailDelUsuarioDelToken) throws ESBException, JMSException;
}
