package com.esprit.voyage.entity;
import java.io.IOException;
import java.util.List;
 
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class EventExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
	private List<Event> listEvents;
    
    public EventExcelExporter(List<Event> listEvents) {
        this.listEvents = listEvents;
        workbook = new XSSFWorkbook();
    }
 
 
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Users");
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
        createCell(row, 0, "User ID", style);  
        createCell(row, 1, "LIBELLE", style);       
        createCell(row, 2, "ADRESSE", style);       
        createCell(row, 3, "DSCRIPTION", style);       
        createCell(row, 4, "DATE-DEBUT", style);       
        createCell(row, 5, "DATE-FIN", style);    
        createCell(row, 6, "NOMBRE-DE-PLACES", style);
        createCell(row, 7, "NOMBRE-DE-PARTICIPANTS", style);
        createCell(row, 8, "POURCENTAGE PARTICIPATION", style);
        
        
         
    }
     
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
     
    private void writeDataLines() {
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
                 
        for (Event event : listEvents) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(row, columnCount++, event.getId(), style);
            createCell(row, columnCount++, event.getLibellé(), style);
            createCell(row, columnCount++, event.getAdresse(), style);
            createCell(row, columnCount++, event.getDescription(), style);
            createCell(row, columnCount++, event.getDateDebutEvent().toString(), style);
            createCell(row, columnCount++, event.getDateFinEvent().toString(), style);
            createCell(row, columnCount++, event.getNbrDePlaces(), style);
            createCell(row, columnCount++, event.getNbrParticipants(), style);
            //createCell(row, columnCount++,event.getNbrParticipants()/event.getNbrDePlaces()*100, style);
            



             
        }
    }
     
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
         
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
         
    }
    

}
