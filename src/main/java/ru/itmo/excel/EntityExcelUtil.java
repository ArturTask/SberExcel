package ru.itmo.excel;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import ru.itmo.entity.EmployeePOJO;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class EntityExcelUtil {
    private static final float MROT = 16242;

    // try means null if not valid
    public static EmployeePOJO tryGetEmployeeFromExcel(XSSFRow row ){
        int lastCol = EmployeePOJO.class.getDeclaredFields().length;
        XSSFCell cell;
        EmployeePOJO employee = new EmployeePOJO();
        for(int c = 0; c < lastCol; c++) {
            cell = row.getCell(c);
            switch (c){
                //id
                case 0:{
                    try {
                        if(cell == null) {
                            return null;
                        }
                        double id = cell.getNumericCellValue();
                        if(id<0 || id%1 != 0){
                            return null;
                        }
                        employee.setId((int) id);
                    }
                    catch (IllegalStateException e){ // wrong type
                        return null;
                    }
                    break;
                }
                //name
                case 1:{
                    try {
                        if(cell == null) {
                            return null;
                        }
                        String name = cell.getStringCellValue();
                        if (name.equals("")){
                            return null;
                        }
                        employee.setName(name);
                    }
                    catch (IllegalStateException e){ // wrong type
                        return null;
                    }

                    break;
                }
                //lastName
                case 2:{
                    try {
                        if(cell == null) {
                            return null;
                        }
                        String lastName = cell.getStringCellValue();
                        if (lastName.equals("")){
                            return null;
                        }
                        employee.setLastName(lastName);
                    }
                    catch (IllegalStateException e){ // wrong type
                        return null;
                    }
                    break;
                }
                //birthday
                case 3:{
                    try {
                        if(cell == null) {
                            employee.setBirthday(null);
                            break;
                        }
                        LocalDate birthday = cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        LocalDate today = LocalDate.now();
                        long fullYears = ChronoUnit.YEARS.between(birthday, today);
                        if (fullYears < 18){
                            return null;
                        }
                        employee.setBirthday(birthday);
                    }
                    catch (IllegalStateException e){ // wrong type
                        return null;
                    }
                    break;
                }
                //company
                case 4:{
                    try {
                        if(cell == null) {
                            employee.setCompany(null);
                            break;
                        }
                        String company = cell.getStringCellValue();
                        employee.setCompany(company);
                    }
                    catch (IllegalStateException e){ // wrong type
                        return null;
                    }
                    break;
                }
                //positionAtWork
                case 5:{
                    try {
                        if(cell == null) {
                            employee.setPositionAtWork(null);
                            break;
                        }
                        String positionAtWork = cell.getStringCellValue();
                        employee.setPositionAtWork(positionAtWork);
                    }
                    catch (IllegalStateException e){ // wrong type
                        return null;
                    }
                    break;
                }
                //salary
                case 6:{
                    try {
                        if(cell == null) {
                            employee.setSalary(0);
                            break;
                        }
                        float salary = (float) cell.getNumericCellValue();
                        if(salary< MROT){
                            return null;
                        }
                        employee.setSalary(salary);
                    }
                    catch (IllegalStateException e){ // wrong type
                        return null;
                    }
                    break;
                }
            }
        }
        return employee;
    }
}
