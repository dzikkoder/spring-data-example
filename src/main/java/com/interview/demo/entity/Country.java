package com.interview.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "countries")
    @JsonBackReference
    private Set<Continent> continents = new HashSet<>();

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private Set<City> cities = new HashSet<>();

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

    public Set<City> getCities() {
        return cities;
    }

    public void setCity(City city) {
        this.cities.add(city);
    }

    public Set<Continent> getContinents() {
        return continents;
    }

    public void setContinent(List<Continent> continents) {
        this.continents.addAll(continents);
    }
}
