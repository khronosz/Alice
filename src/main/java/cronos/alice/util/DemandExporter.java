package cronos.alice.util;

import java.io.ByteArrayInputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cronos.alice.model.dto.DemandDto;

public class DemandExporter extends ExcelExporter {

	private final XSSFWorkbook workbook;
	private final XSSFSheet sheet;
	List<DemandDto> demands;

	public DemandExporter(final List<DemandDto> demands, String projectName) {
		this.demands = demands;
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Demands of " + projectName + "_" + today);
	}

	@Override
	public void writeHeaderRow() {
		Row row = sheet.createRow(0);
		this.initHeader(row);
		this.formatHeader(row, workbook);
	}

	private void initHeader(final Row row) {
		row.createCell(0).setCellValue("Name");
		row.createCell(1).setCellValue("Mib");
		row.createCell(2).setCellValue("User");
		row.createCell(3).setCellValue("Utilization");
		row.createCell(4).setCellValue("Project Join");
		row.createCell(5).setCellValue("Project Leave");
	}

	@Override
	public void writeDataRows() {
		final AtomicInteger rowNumber = new AtomicInteger(1);
		demands.forEach(demand -> {
			Row row = sheet.createRow(rowNumber.getAndIncrement());
			row.createCell(0).setCellValue(demand.getName());
			row.createCell(1).setCellValue(demand.getMib());
			row.createCell(2).setCellValue(demand.getUsername());
			row.createCell(3).setCellValue(demand.getUtilization() == null ? "0" : demand.getUtilization().toString());
			row.createCell(4).setCellValue(demand.getProjectStart() == null ? "" : demand.getProjectStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			row.createCell(5).setCellValue(demand.getProjectEnd() == null ? "" : demand.getProjectEnd().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
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
