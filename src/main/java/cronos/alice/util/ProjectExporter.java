package cronos.alice.util;

import java.io.ByteArrayInputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cronos.alice.model.dto.DemandDto;
import cronos.alice.model.dto.ProjectDto;

public class ProjectExporter extends ExcelExporter {

    private final XSSFWorkbook workbook;
    private final XSSFSheet sheet;
    private final Map<ProjectDto, List<DemandDto>> projects;

    private final Font projectFont;
    private final Font demandFont;

    public ProjectExporter(final Map<ProjectDto, List<DemandDto>> projects) {
        this.projects = projects;
        workbook = new XSSFWorkbook();
        projectFont = workbook.createFont();
        projectFont.setBold(true);
        projectFont.setFontHeightInPoints((short) 10);
        demandFont = workbook.createFont();
        demandFont.setItalic(true);
        demandFont.setFontHeightInPoints((short) 8);
        sheet = workbook.createSheet("Projects_" + today);
    }

    @Override
    public void writeHeaderRow() {
        Row row = sheet.createRow(0);
        this.initHeader(row);
        this.formatHeader(row, workbook);
    }

    private void initHeader(final Row row) {
        row.createCell(0).setCellValue("Name");
        row.createCell(1).setCellValue("SAP Number");
        row.createCell(2).setCellValue("Phase");
        row.createCell(3).setCellValue("Status");
        row.createCell(4).setCellValue("Manager");
        row.createCell(5).setCellValue("Backup Manager");
        row.createCell(6).setCellValue("Owner");
        row.createCell(7).setCellValue("Customer");
        row.createCell(8).setCellValue("Business Unit");
        row.createCell(9).setCellValue("Business Unit HU");
        row.createCell(10).setCellValue("Contact Person");
        row.createCell(11).setCellValue("FTE");
        row.createCell(12).setCellValue("Project Start");
        row.createCell(13).setCellValue("Project End");
        row.createCell(14).setCellValue("Order Type");
        row.createCell(15).setCellValue("Project Type");
        row.createCell(16).setCellValue("Description");
        row.createCell(17).setCellValue("Comment");
    }

    @Override
    public void writeDataRows() {
        final AtomicInteger rowNumber = new AtomicInteger(1);
        projects.forEach((p, d) -> {
            final Row row = sheet.createRow(rowNumber.getAndIncrement());
            row.createCell(0).setCellValue(p.getProjectName());
            row.createCell(1).setCellValue(p.getSap());
            row.createCell(2).setCellValue(p.getPhase());
            row.createCell(3).setCellValue(p.getStatus());
            row.createCell(4).setCellValue(p.getManager());
            row.createCell(5).setCellValue(p.getBackupManager());
            row.createCell(6).setCellValue(p.getOwner());
            row.createCell(7).setCellValue(p.getCustomer());
            row.createCell(8).setCellValue(p.getBu());
            row.createCell(9).setCellValue(p.getBuHu());
            row.createCell(10).setCellValue(p.getContactPerson());
            row.createCell(11).setCellValue(p.getFte());
            row.createCell(12).setCellValue(p.getStart() == null ? "" : p.getStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            row.createCell(13).setCellValue(p.getEnd() == null ? "" : p.getEnd().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            row.createCell(14).setCellValue(p.getOrderType());
            row.createCell(15).setCellValue(p.getProjectType());
            row.createCell(16).setCellValue(p.getDescription());
            row.createCell(17).setCellValue(p.getComment());
            this.formatProjectLine(row);
            d.forEach(demand -> {
                final Row subRow = sheet.createRow(rowNumber.getAndIncrement());
                subRow.createCell(1).setCellValue(demand.getName());
                subRow.createCell(2).setCellValue(demand.getMib());
                subRow.createCell(3).setCellValue(demand.getUsername());
                subRow.createCell(4).setCellValue(demand.getUtilization() == null ? "0" : demand.getUtilization().toString());
                subRow.createCell(5).setCellValue(demand.getProjectStart() == null ? "" : demand.getProjectStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                subRow.createCell(6).setCellValue(demand.getProjectEnd() == null ? "" : demand.getProjectEnd().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                this.formatDemandLine(subRow);
            });
        });
        IntStream.range(0, sheet.getRow(0).getPhysicalNumberOfCells()).forEach(sheet::autoSizeColumn);
    }

    private void formatProjectLine(final Row row) {
        CellStyle style = workbook.createCellStyle();
        style.setFont(projectFont);
        IntStream.range(0, row.getPhysicalNumberOfCells()).forEach(i -> row.getCell(i).setCellStyle(style));
    }

    private void formatDemandLine(final Row row) {
        CellStyle style = workbook.createCellStyle();
        style.setFont(demandFont);
        IntStream.range(1, row.getPhysicalNumberOfCells() + 1).forEach(i -> row.getCell(i).setCellStyle(style));
    }

    public ByteArrayInputStream export(final HttpServletResponse response) {
        this.setResponse(response);
        this.writeHeaderRow();
        this.writeDataRows();
        return this.export(workbook);
    }
}
