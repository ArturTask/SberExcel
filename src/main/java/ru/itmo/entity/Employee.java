package ru.itmo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Employee implements Serializable {

    private int id;
    private String name;
    private String lastName;
    private LocalDate birthday;
    private String company;
    private String positionAtWork;
    private float salary;

}
