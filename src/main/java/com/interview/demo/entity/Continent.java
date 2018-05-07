package com.interview.demo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Continent {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany
    @JoinTable(name = "continent_country",
            joinColumns = {@JoinColumn(name = "fk_continent")},
            inverseJoinColumns = {@JoinColumn(name = "fk_country")})
    private List<Country> countries = new ArrayList<Country>();

    public Continent() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName() {
        this.name = name;
    }

    public void setCountry(Country country) {
        this.countries.add(country);
    }
}
