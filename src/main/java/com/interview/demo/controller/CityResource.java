package com.interview.demo.controller;

import com.interview.demo.entity.City;
import com.interview.demo.entity.Country;
import com.interview.demo.exception.CityNotFoundException;
import com.interview.demo.exception.CountryNotFoundException;
import com.interview.demo.repository.CityRepository;
import com.interview.demo.repository.ContinentRepository;
import com.interview.demo.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

@RestController
public class CityResource {


    @Autowired
    private ContinentRepository continentRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CityRepository cityRepository;

    @PersistenceContext
    private EntityManager em;


    @GetMapping("/cities")
    public Iterable<City> retrieveAllCities() {
        return cityRepository.findAll();
    }

    @GetMapping("/cities/{id}")
    public City getCityById(@PathVariable Long id) throws CityNotFoundException {
        Optional<City> city = cityRepository.findById(id);

        if (!city.isPresent())
            throw new CityNotFoundException("id:" + id);

        return city.get();
    }

    @GetMapping("/countries/{id}/cities")
    public Set<City> getCitiesInCountryWithId(@PathVariable Long id) throws CountryNotFoundException {
        Optional<Country> country = countryRepository.findById(id);

        if (!country.isPresent())
            throw new CountryNotFoundException("id:" + id);

        return country.get().getCities();
    }

    @PostMapping("/countries/{id}/cities")
    public City createCity(@PathVariable(value = "id") Long countryId,
                           @Valid @RequestBody City city) throws CountryNotFoundException {
        return countryRepository.findById(countryId).map(country -> {
            country.setCity(city);
            city.setCountry(country);
            return cityRepository.save(city);
        }).orElseThrow(() -> new CountryNotFoundException("CountryId " + countryId + " not found"));
    }

    @Transactional
    @DeleteMapping("/cities/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable Long id) throws CityNotFoundException {
        return cityRepository.findById(id).map(city -> {
            Country associatedCountry = city.getCountry();
            associatedCountry.getCities().remove(city);
            em.remove(city);

            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new CityNotFoundException("CityId " + id + " not found"));
    }
}
