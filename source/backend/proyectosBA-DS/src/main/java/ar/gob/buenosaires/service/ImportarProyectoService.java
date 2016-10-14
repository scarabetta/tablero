package ar.gob.buenosaires.service;

import java.io.IOException;
import java.util.List;

import javax.jms.JMSException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.importador.MensajeError;
import ar.gob.buenosaires.importador.ResultadoProcesamiento;
import ar.gob.buenosaires.importador.proyecto.SolapaProyecto;
import ar.gob.buenosaires.importador.proyecto.priorizado.ProyectosPriorizadosResultadoProcesamiento;

public interface ImportarProyectoService {

	// Metodos para importacion de proyectos
	ResultadoProcesamiento importarSolapaProyectos(Workbook solpaAImportar, boolean pisarProyectos, String email)
			throws InvalidFormatException, IOException;

	List<MensajeError> validarSolapaProyectos(Workbook solpaAImportar);

	ResultadoProcesamiento previewProyectos(Workbook solpaAImportar);

	SolapaProyecto getSolapaProyecto();

	// Metodos para importacion de proyectos priorizados
	ProyectosPriorizadosResultadoProcesamiento importarSolapaProyectosPriorizados(Workbook solpaAImportar, String email)
			throws InvalidFormatException, IOException, ESBException, JMSException;

	ProyectosPriorizadosResultadoProcesamiento validarSolapaProyectosPriorizados(Workbook solpaAImportar, String userMail);
}
