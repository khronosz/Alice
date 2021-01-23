package cronos.alice.util;

import java.io.ByteArrayInputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cronos.alice.model.dto.UtilPlanDto;

public class UtilPlanExporter extends ExcelExporter {

	private final XSSFWorkbook workbook;
	private final XSSFSheet sheet;
	private final List<UtilPlanDto> users;

	public UtilPlanExporter(final List<UtilPlanDto> users) {
		this.users = users;
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Utilization Plan_" + today);
	}

	public void writeHeaderRow() {
		Row row = sheet.createRow(0);
		this.initHeader(row);
		this.formatHeader(row, workbook);
	}

	private void initHeader(final Row row) {
		row.createCell(0).setCellValue("Name");
		row.createCell(1).setCellValue("Job Title");
		row.createCell(2).setCellValue("Department");
		row.createCell(3).setCellValue("Level");
		row.createCell(4).setCellValue("Direct Manager");
		row.createCell(5).setCellValue("Projects");
		row.createCell(6).setCellValue("Utilization");
		row.createCell(7).setCellValue("Project Leave");
		row.createCell(8).setCellValue("Notes");
	}

	public void writeDataRows() {
		final AtomicInteger rowNumber = new AtomicInteger(1);
		users.forEach(user -> {
			Row row = sheet.createRow(rowNumber.getAndIncrement());
			row.createCell(0).setCellValue(user.getUsername());
			row.createCell(1).setCellValue(user.getJob());
			row.createCell(2).setCellValue(user.getDepartment());
			row.createCell(3).setCellValue(user.getLevel());
			row.createCell(4).setCellValue(user.getDirectManagerName());
			row.createCell(5).setCellValue(user.getProjectNames());
			row.createCell(6).setCellValue(user.getUtilization() == null ? "0" : user.getUtilization().toString());
			row.createCell(7).setCellValue(user.getProjectEnd() == null ? "" : user.getProjectEnd().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			row.createCell(8).setCellValue(user.getNotes());
			this.formatData(row, workbook, sheet);
		});
	}

	public ByteArrayInputStream export(final HttpServletResponse response) {
		this.setResponse(response);
		this.writeHeaderRow();
		this.writeDataRows();
		return this.export(workbook);
	}
}
