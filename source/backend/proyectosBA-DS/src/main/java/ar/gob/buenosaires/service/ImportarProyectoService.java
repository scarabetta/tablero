package ar.gob.buenosaires.service;

import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

import ar.gob.buenosaires.importador.MensajeError;
import ar.gob.buenosaires.importador.proyecto.SolapaProyecto;

public interface ImportarProyectoService {

	Workbook importarSolapaProyectos(Workbook solpaAImportar, boolean pisarProyectos) throws InvalidFormatException, IOException;
	
	List<MensajeError> validarSolapaProyectos(Workbook solpaAImportar);
	
	SolapaProyecto getSolapaProyecto();
}
