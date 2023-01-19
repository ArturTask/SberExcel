package ru.itmo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Table(name = "company")
@Entity
public class Company {

    public Company(String companyName, Set<Position> positions) {
        this.companyName = companyName;
        this.positions = positions;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "company_name", unique = true)
    private String companyName;

    @OneToMany(mappedBy="company", fetch = FetchType.LAZY)
    private Set<Position> positions;


}
