package cronos.alice.util;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public abstract class ExcelExporter {

	private static final Logger log = LogManager.getLogger(ExcelExporter.class);

	protected final String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

	abstract void writeHeaderRow();

	abstract void writeDataRows();

	protected ByteArrayInputStream export(final XSSFWorkbook workbook) {
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			workbook.write(outputStream);
			return new ByteArrayInputStream(outputStream.toByteArray());
		} catch (IOException e) {
			log.error("Excel export failed " + e.getLocalizedMessage());
		}
		return null;
	}

	protected void setResponse(HttpServletResponse response){
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=");
	}

	protected void formatHeader(final Row row, final XSSFWorkbook workbook) {
		Font font = workbook.createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 10);

		CellStyle style = workbook.createCellStyle();
		style.setFont(font);
		style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		IntStream.range(0, row.getPhysicalNumberOfCells()).forEach(i -> row.getCell(i).setCellStyle(style));
	}

	protected void formatData(final Row row, final XSSFWorkbook workbook, final XSSFSheet sheet) {
		Font font = workbook.createFont();
		font.setItalic(true);
		font.setFontHeightInPoints((short) 8);

		CellStyle style = workbook.createCellStyle();
		style.setFont(font);

		IntStream.range(0, row.getPhysicalNumberOfCells()).forEach(i -> {
			row.getCell(i).setCellStyle(style);
			sheet.autoSizeColumn(i);
		});
	}
}
