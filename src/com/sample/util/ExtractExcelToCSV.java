package com.sample.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExtractExcelToCSV {

    private static List<List<XSSFCell>> cellGrid;

    public static String convertExcelToCsv(String ExcelFilePath,String CsvFilePath) throws IOException {
        try {
            cellGrid = new ArrayList<List<XSSFCell>>();
            //FileInputStream myInput = new FileInputStream(ExcelFilePath);
            //POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
            FileInputStream fis = new FileInputStream(ExcelFilePath);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet=workbook.getSheetAt(0);
            Iterator<?> rowIter = sheet.rowIterator();

            while (rowIter.hasNext()) {
            	XSSFRow myRow = (XSSFRow) rowIter.next();
                Iterator<?> cellIter = myRow.cellIterator();
                List<XSSFCell> cellRowList = new ArrayList<XSSFCell>();
                while (cellIter.hasNext()) {
                    XSSFCell myCell = (XSSFCell) cellIter.next();
                    cellRowList.add(myCell);
                }
                cellGrid.add(cellRowList);
            }
        

        File file = new File(CsvFilePath);
        PrintStream stream = new PrintStream(file);
        for (int i = 0; i < cellGrid.size(); i++) {
            List<XSSFCell> cellRowList = cellGrid.get(i);
            for (int j = 0; j < cellRowList.size(); j++) {
            	XSSFCell myCell = (XSSFCell) cellRowList.get(j);
                String stringCellValue = myCell.toString();
                if(j!=cellRowList.size()-1)
                {
                	stream.print(stringCellValue + ",");
                	System.out.println("if: "+stringCellValue);
                }
                else{
                	stream.print(stringCellValue);
                	System.out.println("else: "+stringCellValue);
                }
            }
            stream.println("");
        }
        
        return "PASS";
        
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            
            return "FAIL";
        }
    }

    /*public static void main(String[] args) {
        try {
            convertExcelToCsv();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}