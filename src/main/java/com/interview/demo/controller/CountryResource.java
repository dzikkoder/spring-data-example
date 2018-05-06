package com.interview.demo.controller;

import com.interview.demo.entity.City;
import com.interview.demo.entity.Continent;
import com.interview.demo.entity.Country;
import com.interview.demo.exception.ContinentNotFoundException;
import com.interview.demo.exception.CountryNotFoundException;
import com.interview.demo.repository.ContinentRepository;
import com.interview.demo.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class CountryResource {

    @Autowired
    private ContinentRepository continentRepository;

    @Autowired
    private CountryRepository countryRepository;

    @PersistenceContext
    private EntityManager em;

    @GetMapping("/countries")
    public List<Country> retrieveAllCountries() {
        return countryRepository.findAll();
    }

    @GetMapping("/countries/{id}")
    public Country getCountryById(@PathVariable Long id) throws CountryNotFoundException {
        Optional<Country> country = countryRepository.findById(id);

        if (!country.isPresent())
            throw new CountryNotFoundException("id:" + id);

        return country.get();
    }

    @GetMapping("/countries/{id}/cities")
    public List<City> getCitiesInCountryWithId(@PathVariable Long id) throws CountryNotFoundException {
        Optional<Country> country = countryRepository.findById(id);

        if (!country.isPresent())
            throw new CountryNotFoundException("id:" + id);

        return country.get().getCities();
    }

    @PostMapping("/continents/{id}/countries")
    public Country createCountryOnContinentWithId(@PathVariable(value = "id") Long continentId,
                                                  @Valid @RequestBody Country country) throws ContinentNotFoundException {
        return continentRepository.findById(continentId).map(continent -> {
            continent.setCountry(country);
            //fun fact: this line will cause a stack overflow on receiving a request on this mapping
            //country.setContinent(Collections.singletonList(continent));
            return countryRepository.save(country);
        }).orElseThrow(() -> new ContinentNotFoundException("ContinentId " + continentId + " not found"));
    }

    @Transactional
    @DeleteMapping("/countries/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable Long id) throws CountryNotFoundException {
        return countryRepository.findById(id).map(country -> {
            for (Continent associatedContinent : country.getContinents()) {
                Continent continent = em.find(Continent.class, associatedContinent.getId());
                continent.getCountries().remove(country);
          }
            em.remove(country);

            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new CountryNotFoundException("CountryId " + id + " not found"));
    }
}
