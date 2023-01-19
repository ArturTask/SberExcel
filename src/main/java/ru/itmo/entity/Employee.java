package ru.itmo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class Employee implements Serializable {

    @Id
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "company")
    private String company;

    @Column(name = "positionAtWork")
    private String positionAtWork;

    @Column(name = "salary")
    private float salary;

}
