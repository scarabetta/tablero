package ar.gob.buenosaires.service;

import java.util.List;

import javax.jms.JMSException;

import com.fasterxml.jackson.databind.JsonNode;

import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface ProyectoService {

	List<Proyecto> getProyectos() throws ESBException, JMSException;

	Proyecto getProyectoPorId(String id) throws ESBException, JMSException;

	Proyecto getProyectoPorCodigo(String codigo) throws ESBException, JMSException;

	Proyecto getProyectoByName(String nombre) throws ESBException, JMSException;

	Proyecto createProyecto(Proyecto proyecto) throws ESBException, JMSException;

	Proyecto updateProyecto(Proyecto proyecto) throws ESBException, JMSException;

	void deleteProyecto(String id) throws ESBException, JMSException;

	Proyecto presentarProyecto(Proyecto proyecto) throws ESBException, JMSException;

	Proyecto cancelarProyecto(Proyecto proyecto) throws ESBException, JMSException;

	Proyecto verificarProyecto(Proyecto proyecto) throws ESBException, JMSException;

	Proyecto deshacerCancelacion(Proyecto proyecto) throws ESBException, JMSException;

	Proyecto cambiarEstadoProyecto(Proyecto proyecto, String action) throws ESBException, JMSException;

	List<String> getAccionesPermitidas(String idProyecto) throws ESBException, JMSException;

	JsonNode getResumenProyectosPriorizacion() throws ESBException, JMSException;

	void cancelarPriorizacionDeProyectos() throws ESBException, JMSException;

	void updatePriorizarProyectos() throws ESBException, JMSException;

}
