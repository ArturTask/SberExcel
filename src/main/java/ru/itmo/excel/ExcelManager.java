package ru.itmo.excel;

import lombok.Cleanup;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.itmo.dao.EmployeeDao;
import ru.itmo.entity.Employee;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static ru.itmo.excel.EntityExcelUtil.tryGetEmployeeFromExcel;

public class ExcelManager {
    private static EmployeeDao employeeDao = new EmployeeDao();

    public static Map<Integer, Employee> getEmployees(String path, int sheetIdx, int fromRow, int toRow) throws IOException {
        File myFile = new File(path);
        @Cleanup
        FileInputStream fis = new FileInputStream(myFile);
        XSSFWorkbook workbook = new XSSFWorkbook (fis);
        XSSFSheet sheet = workbook.getSheetAt(sheetIdx);

        return readData(sheet, fromRow, toRow);

    }

    private static Map<Integer, Employee> readData(XSSFSheet sheet, int fromRow, int toRow) {
        XSSFRow row;
        Map<Integer, Employee> result = new HashMap<>();

        for(int r = fromRow; r < toRow; r++) {
            row = sheet.getRow(r);
            if(row == null) {
                break; //!ATTENTION must be NO EMPTY rows between useful information
            }
            Employee entity = (Employee) tryCreate(row, result); // disposable, change if needed
            if (entity == null){
                continue;
            }
            result.put(entity.getId(), entity);

        }
        return result;
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
    public static void main(String[] args) throws IOException {
//        String path = "test.xlsx";
//        File myFile = new File(path);
//        @Cleanup
//        FileInputStream fis = new FileInputStream(myFile);
//        XSSFWorkbook workbook = new XSSFWorkbook (fis);
//        XSSFSheet sheet = workbook.getSheetAt(0);
//        System.out.println("ssss");
//
        Map<Integer, Employee> employees = getEmployees("test.xlsx", 0, 1, 6);
        employeeDao.save( employees.get(1));
        System.out.println("la");
    }

}
