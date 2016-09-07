package ar.gob.buenosaires.exportador.consumers;

import java.util.Iterator;
import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTable;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumn;

import ar.gob.buenosaires.exportador.IExportableAExcel;

public class CreadorDinamicoDeColumnasConsumer implements Consumer<IExportableAExcel> {
	private CTTable ctTable;
	private int indexNuevaColumna;
	private XSSFSheet sheetAt0;
	private int numeroFilaProcesando;

	public CreadorDinamicoDeColumnasConsumer(XSSFSheet sheetAt0, int numeroFilaProcesando) {
		this.numeroFilaProcesando = numeroFilaProcesando;
		indexNuevaColumna = sheetAt0.getRow(1).getLastCellNum();
		this.sheetAt0 = sheetAt0;
		ctTable = sheetAt0.getTables().get(0).getCTTable();
	}

	@Override
	public void accept(IExportableAExcel t) {
		int celdaAMarcar = 0;
		XSSFRow filaProcesando = sheetAt0.getRow(numeroFilaProcesando);
		String nombreParaExportacion = t.getNombreParaExportacion();

		for (Iterator<Cell> celdaIterator = sheetAt0.getRow(1).cellIterator(); celdaIterator.hasNext()
				&& celdaAMarcar == 0;) {
			Cell celda = celdaIterator.next();
			if (celda.getStringCellValue().equalsIgnoreCase(nombreParaExportacion)) {
				celdaAMarcar = celda.getColumnIndex();
			}
		}

		if (celdaAMarcar == 0) {
			CTTableColumn addNewTableColumn = ctTable.getTableColumns().addNewTableColumn();
			addNewTableColumn.setId(indexNuevaColumna + 100);
			addNewTableColumn.setName(nombreParaExportacion);
			ctTable.getTableColumns().setCount(ctTable.getTableColumns().getCount() + 1);
			XSSFCell cell = sheetAt0.getRow(1).getCell(indexNuevaColumna, Row.CREATE_NULL_AS_BLANK);
			cell.setCellValue(nombreParaExportacion);
			celdaAMarcar = cell.getColumnIndex();
			indexNuevaColumna++;
		}

		filaProcesando.getCell(celdaAMarcar, MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue("X");
	}
}