package ru.itmo.entity;

import javafx.geometry.Pos;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    @Id
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birthday")
    private LocalDate birthday;

    @ManyToOne(targetEntity = Position.class)
    @JoinColumn(name="position_id")
    private Position positionAtWork;

    @Column(name = "salary")
    private float salary;

}
