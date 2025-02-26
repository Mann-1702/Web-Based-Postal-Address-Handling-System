package edu.seattleu.addressmanager.controller;


import edu.seattleu.addressmanager.model.City;
import edu.seattleu.addressmanager.model.Country;
import edu.seattleu.addressmanager.model.StateProvince;
import edu.seattleu.addressmanager.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/location")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    // --- Countries ---
    @Operation(summary = "Get all countries")
    @GetMapping("/countries")
    public List<Country> getAllCountries() {
        return locationService.findAllCountries();
    }

    @Operation(summary = "Create or update country")
    @PostMapping("/countries")
    public Country createCountry(@RequestBody Country c) {
        return locationService.saveCountry(c);
    }

    // --- States ---
    @Operation(summary = "Get all states")
    @GetMapping("/states")
    public List<StateProvince> getAllStates() {
        return locationService.findAllStates();
    }

    @Operation(summary = "Get states by country ID")
    @GetMapping("/countries/{countryId}/states")
    public List<StateProvince> getStatesForCountry(@PathVariable Long countryId) {
        return locationService.findStatesByCountry(countryId);
    }

    @Operation(summary = "Create or update state")
    @PostMapping("/states")
    public StateProvince createState(@RequestBody StateProvince s) {
        return locationService.saveState(s);
    }

    // --- Cities ---
    @Operation(summary = "Get all cities")
    @GetMapping("/cities")
    public List<City> getAllCities() {
        return locationService.findAllCities();
    }

    @Operation(summary = "Get cities by state ID")
    @GetMapping("/states/{stateId}/cities")
    public List<City> getCitiesForState(@PathVariable Long stateId) {
        return locationService.findCitiesByState(stateId);
    }

    @Operation(summary = "Create or update city")
    @PostMapping("/cities")
    public City createCity(@RequestBody City city) {
        return locationService.saveCity(city);
    }
}