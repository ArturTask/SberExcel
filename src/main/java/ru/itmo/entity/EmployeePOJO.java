package ru.itmo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePOJO {

    private int id;

    private String name;

    private String lastName;

    private LocalDate birthday;

    private String company;

    private String positionAtWork;

    private float salary;
}
