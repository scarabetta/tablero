package ar.gob.buenosaires.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.jms.JMSException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.FormulaParseException;
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
		getSolapaProyecto().setSolapa(solpaAImportar.getSheetAt(0));
		getSolapaProyecto().configurarSolapa();
		getSolapaProyecto().validarSolapa();

		return getSolapaProyecto().getValidador().getProblemasSolapa();
	}

	@Override
	public Workbook importarSolapaProyectos(Workbook solpaAImportar, boolean pisarProyectos)
			throws InvalidFormatException, IOException {

		getSolapaProyecto().setSolapa(solpaAImportar.getSheetAt(0));
		getSolapaProyecto().configurarSolapa(pisarProyectos);

		List<MensajeError> problemasSolpa = getSolapaProyecto().validarSolapa();

		if (problemasSolpa.isEmpty()) {
			ImportadorProyectoBuilder builder = null;
			boolean laProximaEsUltimaFila = false;
			Row unaFila = null;

			for (int i = getSolapaProyecto().getNumeroFilaInicioImportacion(); i <= getSolapaProyecto()
					.getNumeroUltimaFila() && !laProximaEsUltimaFila; i++) {
				unaFila = getSolapaProyecto().getFilaNumero(i);
				builder = new ImportadorProyectoBuilder(serviceFactory);
				builder.setJurisdiccion(getSolapaProyecto().getJurisdiccion());

				try {
					if (getSolapaProyecto().validarEImportarFila(unaFila, builder)) {
						builder.build();
					}
				} catch (ESBException | JMSException e) {
					LOGGER.error("Hubo un error en la fila " + unaFila.getRowNum() + "\n" + e.getMessage());
					getSolapaProyecto().getValidador().agregarMensajeParaFila(unaFila.getRowNum(),
							"Hubo un error inesperado en la fila " + unaFila.getRowNum() + " al ser gurdada en la base de datos.",
							MensajeError.TIPO_ERROR);
				}
				laProximaEsUltimaFila = (getSolapaProyecto().getFilaNumero(i + 1) == null)
						|| getSolapaProyecto().esUltimaFila(getSolapaProyecto().getFilaNumero(i + 1));

			}
		}

		if (!getSolapaProyecto().getValidador().getProblemasSolapa().isEmpty()) {
			StringBuilder erroresSolapa = new StringBuilder();
			for (Iterator<MensajeError> iterator = getSolapaProyecto().getValidador().getProblemasSolapa()
					.iterator(); iterator.hasNext();) {
				MensajeError mensajeError = iterator.next();
				erroresSolapa.append(mensajeError.getMensaje()).append("\n");
			}

			getSolapaProyecto().getSolapa().getRow(0).getCell(3, Row.CREATE_NULL_AS_BLANK)
			.setCellValue(erroresSolapa.toString());

			return solpaAImportar;
		}

		// Si hay errores, borro las filas que se importaron bien y deja las
		// malas
		if (!getSolapaProyecto().getValidador().getProblemasFilas().isEmpty()) {
			return crearExcelErroresSiEsNecesario();

		} else {
			return null;

		}

	}

	@Override
	public SolapaProyecto getSolapaProyecto() {
		return solapaProyecto;
	}

	public XSSFWorkbook crearExcelErroresSiEsNecesario() throws InvalidFormatException, IOException {
		XSSFWorkbook wb = null;
		try {
			FileInputStream fis = new FileInputStream(env.getProperty("download.template.proyecto.path"));
			wb = new XSSFWorkbook(fis);
			Sheet solapaProyectoTemplate = wb.getSheetAt(0);
			int numeroFilaInicioImportacion = getSolapaProyecto().getNumeroFilaInicioImportacion();
			int index = 0;
			int numeroNuevaFila;
			solapaProyectoTemplate.getRow(0).getCell(1).setCellValue("Primero seleccioná tu jurisdicción");

			for (Integer numeroFilaConError : getSolapaProyecto().getValidador().getProblemasFilas().keySet()) {
				numeroNuevaFila = numeroFilaInicioImportacion + index;
				getSolapaProyecto().copiarFilas(getSolapaProyecto().getFilaNumero(numeroFilaConError),
						solapaProyectoTemplate.createRow(numeroNuevaFila));

				index++;

			}
			fis.close();
		} catch (FileNotFoundException | FormulaParseException | IllegalStateException e) {
			LOGGER.debug("Hubo un problema leyendo el archivo template : \n" + e.getMessage());
			e.printStackTrace();
		}
		return wb;

	}

}
