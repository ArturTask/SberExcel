package ru.itmo.excel;

import lombok.Cleanup;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.itmo.entity.EmployeePOJO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.itmo.excel.EntityExcelUtil.tryGetEmployeeFromExcel;

public class ExcelManager {
    private static XSSFWorkbook workbook;


    public static Map<Integer, EmployeePOJO> getEmployees(String path, int sheetIdx, int fromRow, int toRow) throws IOException {
        if (workbook==null) {
            File myFile = new File(path);
            @Cleanup
            FileInputStream fis = new FileInputStream(myFile);
            workbook = new XSSFWorkbook(fis);
        }
        XSSFSheet sheet = workbook.getSheetAt(sheetIdx);

        return readData(sheet, fromRow, toRow);

    }

    public static void saveEmployeesToExcel(String path, int sheetIdx, List<EmployeePOJO> employees) throws IOException {
        System.out.println("Opening excel - "+ new SimpleDateFormat("HH:mm:ss").format(new Date()));
        File myFile = new File(path);
        @Cleanup
        FileInputStream fis = new FileInputStream(myFile);
        if (workbook==null) {
            workbook = new XSSFWorkbook(fis);
        }
        XSSFSheet sheet = workbook.getSheetAt(sheetIdx);
        System.out.println("Opened excel, writing data... - "+ new SimpleDateFormat("HH:mm:ss").format(new Date()));
        writeData(sheet, employees);
        System.out.println("Saving... - "+ new SimpleDateFormat("HH:mm:ss").format(new Date()));
        workbook.write(Files.newOutputStream(Paths.get(path)));


    }

    private static Map<Integer, EmployeePOJO> readData(XSSFSheet sheet, int fromRow, int toRow) {
        XSSFRow row;
        Map<Integer, EmployeePOJO> result = new HashMap<>();

        for(int r = fromRow; r < toRow; r++) {
            row = sheet.getRow(r);
            if(row == null) {
                break; //!ATTENTION must be NO EMPTY rows between useful information
            }
            EmployeePOJO entity = (EmployeePOJO) tryCreate(row, result); // disposable, change if needed
            if (entity == null){
                continue;
            }
            result.put(entity.getId(), entity);

        }
        return result;
    }

    private static void writeData(XSSFSheet sheet, List<EmployeePOJO> employees) {
        XSSFRow row;
        for(int r = 0; r < employees.size(); r++) {
            row = sheet.getRow(r);
            if (row==null){
                row = sheet.createRow(r);
            }
            writeEmployeeToExcel(row, employees.get(r));
        }

    }

    private static void writeEmployeeToExcel(XSSFRow row, EmployeePOJO employee){

        for (int i =0; i< EmployeePOJO.class.getDeclaredFields().length; i++){
            XSSFCell cell = row.getCell(i);
            if (cell == null){
                cell = row.createCell(i);
            }
            switch (i){
                //id
                case 0:{
                    cell.setCellValue(employee.getId());
                    break;
                }
                //name
                case 1:{
                    cell.setCellValue(employee.getName());
                    break;
                }
                //lastName
                case 2:{
                    cell.setCellValue(employee.getLastName());
                    break;
                }
                //birthday
                case 3:{
                    cell.setCellValue(Date.from(employee.getBirthday().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    break;
                }
                //company
                case 4:{
                    cell.setCellValue(employee.getCompany());
                    break;
                }
                //positionAtWork
                case 5:{
                    cell.setCellValue(employee.getPositionAtWork());
                    break;
                }
                //salary
                case 6:{
                    cell.setCellValue(employee.getSalary());
                    break;
                }
            }
        }
    }

    // try means null if not valid
    private static Object tryCreate(XSSFRow row, Map<Integer, ?> allEntities) {

        try{
            //check first row id if is already present
            Integer numericCellValue = (int) row.getCell(0).getNumericCellValue();
            if(allEntities.containsKey(numericCellValue)){
                return null;
            }
            return tryGetEmployeeFromExcel(row); // disposable, change if needed
        }
        catch (IllegalStateException e){ // wrong type
            return null;
        }


    }


    //debug
//    public static void main(String[] args) throws IOException, InterruptedException {
//
//        String path = "test.xlsx";
//        File myFile = new File(path);
//        @Cleanup
//        FileInputStream fis = new FileInputStream(myFile);
//        XSSFWorkbook workbook = new XSSFWorkbook (fis);
//        XSSFSheet sheet = workbook.getSheetAt(0);
//
//    }

}
