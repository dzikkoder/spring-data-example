package com.interview.demo.controller;

import com.interview.demo.entity.Continent;
import com.interview.demo.entity.Country;
import com.interview.demo.exception.ContinentNotFoundException;
import com.interview.demo.repository.ContinentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class ContinentResource {

    @Autowired
    private ContinentRepository continentRepository;

    @GetMapping("/continents")
    public List<Continent> retrieveAllContinents() {
        return continentRepository.findAll();
    }

    @GetMapping("/continents/{id}")
    public Continent getContinentById(@PathVariable Long id) throws ContinentNotFoundException {
        Optional<Continent> continent = continentRepository.findById(id);

        if (!continent.isPresent())
            throw new ContinentNotFoundException("id:" + id);

        return continent.get();
    }

    @GetMapping("/continents/{id}/countries")
    public Set<Country> getCountriesInContinentWithId(@PathVariable Long id) throws ContinentNotFoundException {
        Optional<Continent> continent = continentRepository.findById(id);

        if (!continent.isPresent())
            throw new ContinentNotFoundException("id:" + id);

        return continent.get().getCountries();
    }

    @PostMapping("/continents")
    public Continent createContinent(@Valid @RequestBody Continent continent) {
        return continentRepository.save(continent);
    }

    @DeleteMapping("/continents/{id}")
    public ResponseEntity<?> deleteContinent(@PathVariable Long id) throws ContinentNotFoundException {
        return continentRepository.findById(id).map(continent -> {
            continentRepository.delete(continent);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ContinentNotFoundException("ContinentId " + id + " not found"));
    }
}
