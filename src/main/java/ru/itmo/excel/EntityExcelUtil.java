package ru.itmo.excel;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import ru.itmo.entity.Employee;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class EntityExcelUtil {
    private static final float MROT = 16242;

    // try means null if not valid
    public static Employee tryGetEmployeeFromExcel(XSSFRow row ){
        int lastCol = Employee.class.getDeclaredFields().length;
        XSSFCell cell;
        Employee employee = new Employee();
        for(int c = 0; c < lastCol; c++) {
            cell = row.getCell(c);
            if(cell == null) {
                return null;
            }
            switch (c){
                //id
                case 0:{
                    try {
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