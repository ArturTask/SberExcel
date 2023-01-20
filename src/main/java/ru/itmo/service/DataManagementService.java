package ru.itmo.service;

import ru.itmo.dao.EmployeeDao;
import ru.itmo.dao.PositionDao;
import ru.itmo.entity.Employee;
import ru.itmo.entity.EmployeePOJO;
import ru.itmo.entity.Position;

import javax.persistence.PersistenceException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static ru.itmo.excel.ExcelManager.getEmployees;

public class DataManagementService {

    private static final EmployeeDao employeeDao = new EmployeeDao();
    private static final PositionDao positionDao = new PositionDao();
    private static final int LAST_ROW = 500001;
    private static final List<Position> allPositions = positionDao.getAllPositions();



    public static void main(String[] args) {
        System.out.println("Start - "+ new SimpleDateFormat("HH:mm:ss").format(new Date()));
        smartSaveAll(5);
        System.out.println("End - "+ new SimpleDateFormat("HH:mm:ss").format(new Date()));

    }

    private static void smartSaveAll(int divisionNumber){
        int averageQuantity = LAST_ROW / divisionNumber;
        int currentLastRow = averageQuantity;

        while (currentLastRow < LAST_ROW){
            try {
                Map<Integer, EmployeePOJO> employees = getEmployees("test.xlsx", 0, currentLastRow - averageQuantity, currentLastRow);
                employees.forEach((integer, employeePOJO) -> {
                    trySave(employeePOJO);
                });
            } catch (IOException e) {
                System.out.println("Excel Exception\n");
                e.printStackTrace();
            }
            currentLastRow = Math.min(currentLastRow + averageQuantity, LAST_ROW);


        }
    }

    private static void trySave(EmployeePOJO employeePOJO) {

        Employee employee = tryParse(employeePOJO);
        if (employee!=null){
            try {
                employeeDao.save(employee);
            }
            catch (PersistenceException ignore){ // id is already present

            }
        }

    }

    // last check if position and company exist
    private static Employee tryParse(EmployeePOJO employeePOJO) {
        for (Position position: allPositions) {
            if (position.getPositionName().equals(employeePOJO.getPositionAtWork()) && position.getCompany().getCompanyName().equals(employeePOJO.getCompany())) {
                return new Employee(employeePOJO.getId(), employeePOJO.getName(), employeePOJO.getLastName(), employeePOJO.getBirthday(), position, employeePOJO.getSalary());
            }
        }
        return null;

    }

    // do not use!!! Performance goes down 10 times
    private static Employee tryParseUsingBD(EmployeePOJO employeePOJO) {
        Position position = positionDao.tryFindPositionAndCompany(employeePOJO.getPositionAtWork(), employeePOJO.getCompany());
        if (position!=null) {
            return new Employee(employeePOJO.getId(), employeePOJO.getName(), employeePOJO.getLastName(), employeePOJO.getBirthday(), position, employeePOJO.getSalary());
        }

        return null;

    }


}
