package com.esprit.voyage.entity;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class VoyageExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
	private List<Voyage> listVoyage;

    public VoyageExcelExporter(List<Voyage> listVoyage) {
        this.listVoyage = listVoyage;
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
         
        createCell(row, 0, "Voyage ID", style);
        createCell(row, 1, "LIBELLE", style);       
        createCell(row, 2, "Lieu Depart", style);
        createCell(row, 3, "Lieu Arrivee", style);
        createCell(row, 4, "Date Depart", style);
        createCell(row, 5, "DATE-FIN", style);    
        createCell(row, 6, "Date Arrivee", style);
        createCell(row, 7, "Compagnie", style);
        createCell(row, 8, "Tarif", style);
        
        
         
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
                 
        for (Voyage voyage : listVoyage) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(row, columnCount++, voyage.getId(), style);
            createCell(row, columnCount++, voyage.getDescription(), style);
            createCell(row, columnCount++, voyage.getLieuDepart(), style);
            createCell(row, columnCount++, voyage.getLieuArrivee(), style);
            createCell(row, columnCount++, voyage.getDateDepart().toString(), style);
            createCell(row, columnCount++, voyage.getDateArrivee().toString(), style);
            createCell(row, columnCount++, voyage.getCompagnie(), style);
            createCell(row, columnCount++, voyage.getTarif(), style);
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
