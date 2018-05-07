package com.interview.demo.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    private Set<Country> countries = new HashSet<Country>();

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

    public Set<Country> getCountries() {
        return countries;
    }

    public void setCountry(Country country) {
        this.countries.add(country);
    }
}
