package ar.gob.buenosaires.importador.proyecto.priorizado;

import org.apache.poi.ss.usermodel.Cell;

import ar.gob.buenosaires.importador.proyecto.SolapaProyecto;

public class ProyectoPriorizadoCelda {

	private Cell celdaOriginal;

	public ProyectoPriorizadoCelda(Cell cell) {
		celdaOriginal = cell;
	}

	public String getSringValueParaCualquierTipo() {
		return SolapaProyecto.getCellStringValue(celdaOriginal.getCellType(), celdaOriginal);
	}

	/**
	 * retorna el valor double de la celda, si no es de tipo numeric, retorna
	 * null
	 */
	public Double getDoubleValueCeroIfNull() {
		Double returnValue = new Double(0);
		if (Cell.CELL_TYPE_NUMERIC == celdaOriginal.getCellType()) {
			returnValue = celdaOriginal.getNumericCellValue();
		}
		return returnValue;
	}

	public int getNumeroColumna() {
		return celdaOriginal.getColumnIndex();
	}

	public int getNumeroFila() {
		return celdaOriginal.getRowIndex();
	}

}
