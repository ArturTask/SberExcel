package ru.itmo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "company")
@Entity
public class Company {

    public Company(String companyName, Set<Position> positions) {
        this.companyName = companyName;
        this.positions = positions;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "company_name", unique = true)
    private String companyName;

    @OneToMany(mappedBy="company",cascade =CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Position> positions;


}
