package ar.gob.buenosaires.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.jms.JMSException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.importador.MensajeError;
import ar.gob.buenosaires.importador.proyecto.ImportadorProyectoBuilder;
import ar.gob.buenosaires.importador.proyecto.SolapaProyecto;
import ar.gob.buenosaires.service.IServiceFactory;
import ar.gob.buenosaires.service.ImportarProyectoService;

@Service
public class ImportarProyectoServiceImpl implements ImportarProyectoService {

	private final static Logger LOGGER = LoggerFactory.getLogger(ImportarProyectoServiceImpl.class);

	@Autowired
	private IServiceFactory serviceFactory;

	@Autowired
	Environment env;

	@Autowired
	private SolapaProyecto solapaProyecto;

	@Override
	public List<MensajeError> validarSolapaProyectos(Workbook solpaAImportar) {
		this.getSolapaProyecto().setSolapa(solpaAImportar.getSheetAt(0));
		this.getSolapaProyecto().configurarSolapa();
		this.getSolapaProyecto().validarSolapa();

		return this.getSolapaProyecto().getValidador().getProblemasSolapa();
	}

	@Override
	public Workbook importarSolapaProyectos(Workbook solpaAImportar, boolean pisarProyectos)
			throws InvalidFormatException, IOException {

		this.getSolapaProyecto().setSolapa(solpaAImportar.getSheetAt(0));
		this.getSolapaProyecto().configurarSolapa(pisarProyectos);

		List<MensajeError> problemasSolpa = this.getSolapaProyecto().validarSolapa();
		ImportadorProyectoBuilder builder = null;

		if (problemasSolpa.isEmpty()) {

			for (int i = this.getSolapaProyecto().getNumeroFilaInicioImportacion(); i < this.getSolapaProyecto()
					.getNumeroUltimaFila(); i++) {
				Row unaFila = this.getSolapaProyecto().getFilaNumero(i);
				builder = new ImportadorProyectoBuilder(serviceFactory);
				builder.setJurisdiccion(this.getSolapaProyecto().getJurisdiccion());

				try {

					if (this.getSolapaProyecto().validarEImportarFila(unaFila, builder)) {
						builder.build();
					}
				} catch (ESBException | JMSException e) {
					LOGGER.error("Hubo un error en la fila "
							+ unaFila.getRowNum() + "\n" + e.getMessage());
				}

			}
		}

		if (!this.getSolapaProyecto().getValidador().getProblemasSolapa().isEmpty()) {
			StringBuilder erroresSolapa = new StringBuilder();
			for (Iterator<MensajeError> iterator = this.getSolapaProyecto().getValidador().getProblemasSolapa()
					.iterator(); iterator.hasNext();) {
				MensajeError mensajeError = iterator.next();
				erroresSolapa.append(mensajeError.getMensaje()).append("\n");
			}

			this.getSolapaProyecto().getSolapa().getRow(0).getCell(3, Row.CREATE_NULL_AS_BLANK)
					.setCellValue(erroresSolapa.toString());

			return solpaAImportar;
		}

		// Si hay errores, borro las filas que se importaron bien y deja las
		// malas
		if (!this.getSolapaProyecto().getValidador().getProblemasFilas().isEmpty()) {
			return this.crearExcelErroresSiEsNecesario();

		} else {
			return null;

		}

	}

	public SolapaProyecto getSolapaProyecto() {
		return solapaProyecto;
	}

	public XSSFWorkbook crearExcelErroresSiEsNecesario() throws InvalidFormatException, IOException {

		FileInputStream fis = new FileInputStream(env.getProperty("download.template.proyecto.path"));
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		Sheet solapaProyectoTemplate = wb.getSheetAt(0);
		int numeroFilaInicioImportacion = this.getSolapaProyecto().getNumeroFilaInicioImportacion();
		int index = 0;
		int numeroNuevaFila;
		solapaProyectoTemplate.getRow(0).getCell(1)
				.setCellValue(this.getSolapaProyecto().getJurisdiccion().getNombre());

		for (Integer numeroFilaConError : this.getSolapaProyecto().getValidador().getProblemasFilas().keySet()) {
			numeroNuevaFila = numeroFilaInicioImportacion + index;
			this.getSolapaProyecto().copiarFilas(this.getSolapaProyecto().getFilaNumero(numeroFilaConError),
					solapaProyectoTemplate.createRow(numeroNuevaFila));

			index++;

		}
		fis.close();

		return wb;

	}

}
