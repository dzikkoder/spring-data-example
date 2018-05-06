package com.interview.demo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "countries")
    private List<Continent> continents = new ArrayList<Continent>();

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<City> cities;

    public Country() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<City> getCities() {
        return cities;
    }

    public List<Continent> getContinents() {
        return continents;
    }

    public void setContinent(List<Continent> continents) {
        this.continents.addAll(continents);
    }
}
