package ru.itmo.entity;

import javafx.geometry.Pos;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Table(name = "position")
@Entity
public class Position {

    public Position(String positionName) {
        this.positionName = positionName;
    }

    public Position(String positionName, Company company) {
        this.positionName = positionName;
        this.company = company;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "position_name", unique = true)
    private String positionName;

    @ManyToOne(targetEntity= Position.class)
    private Company company;
}
