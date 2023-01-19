package ru.itmo.service;

import ru.itmo.dao.CompanyDao;
import ru.itmo.entity.Company;
import ru.itmo.excel.ExcelManager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataManagementService {

    private static CompanyDao companyDao = new CompanyDao();
    private static List<Company> companies;

    public DataManagementService() {
        companies = companyDao.getAllCompanies();

    }

    public static void main(String[] args) {

    }


}
