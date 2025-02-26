package edu.seattleu.addressmanager.service;
import edu.seattleu.addressmanager.model.City;
import edu.seattleu.addressmanager.model.Country;
import edu.seattleu.addressmanager.model.StateProvince;
import edu.seattleu.addressmanager.repository.CityRepository;
import edu.seattleu.addressmanager.repository.CountryRepository;
import edu.seattleu.addressmanager.repository.StateProvinceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LocationService {
    private final CountryRepository countryRepo;
    private final StateProvinceRepository stateRepo;
    private final CityRepository cityRepo;

    public LocationService(CountryRepository countryRepo,
                           StateProvinceRepository stateRepo,
                           CityRepository cityRepo) {
        this.countryRepo = countryRepo;
        this.stateRepo = stateRepo;
        this.cityRepo = cityRepo;
    }

    // Countries
    public List<Country> findAllCountries() {
         log.info("Fetching all countries...");
        return countryRepo.findAll();
    }
    public Optional<Country> findCountry(Long id) {
         log.info("Finding country by ID = {}", id);
        return countryRepo.findById(id);
    }
    public Country saveCountry(Country c) {
         log.info("Saving country: {}", c);
        return countryRepo.save(c);
    }

    // States
    public List<StateProvince> findAllStates() {
        log.info("Fetching all states...");
        return stateRepo.findAll();
    }
    public List<StateProvince> findStatesByCountry(Long countryId) {
        log.info("Fetching states for country ID = {}", countryId);
        return stateRepo.findByCountry_Id(countryId);
    }
    public StateProvince saveState(StateProvince s) {
        log.info("Saving state: {}", s);
        return stateRepo.save(s);
    }

    // Cities
    public List<City> findAllCities() {
        log.info("Fetching all cities...");
        return cityRepo.findAll();
    }
    public List<City> findCitiesByState(Long stateId) {
        log.info("Fetching cities for state ID = {}", stateId);
        return cityRepo.findByState_Id(stateId);
    }
    public City saveCity(City city) {
        log.info("Saving city: {}", city);
        return cityRepo.save(city);
    }

}
