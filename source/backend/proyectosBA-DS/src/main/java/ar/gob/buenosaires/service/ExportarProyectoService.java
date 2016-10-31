package ar.gob.buenosaires.service;

import java.util.List;

import javax.jms.JMSException;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import ar.gob.buenosaires.domain.ExportacionProyectoView;
import ar.gob.buenosaires.domain.ReporteProyectosView;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface ExportarProyectoService {

	List<ExportacionProyectoView> getAllExportacionProyectoView() throws ESBException, JMSException;

	List<ReporteProyectosView> getAllReporteProyectosView(String mailUsuario) throws ESBException, JMSException;

	void generarExcel(XSSFSheet sheetAt0) throws ESBException, JMSException;

	void generarExcelReporte(XSSFSheet sheetAt0, String mailUsuario) throws ESBException, JMSException;

}
