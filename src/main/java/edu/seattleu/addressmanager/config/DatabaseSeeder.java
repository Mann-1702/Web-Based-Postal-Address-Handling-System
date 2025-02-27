package edu.seattleu.addressmanager.config;


import com.sun.source.tree.UsesTree;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.*;
import com.github.javafaker.Faker;
import edu.seattleu.addressmanager.model.*;
import edu.seattleu.addressmanager.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Slf4j
@Configuration
@Profile("local")  // Run only with local spring profile for local development
public class DatabaseSeeder {
    @Bean
    CommandLineRunner initDatabase(
            CountryRepository countryRepo,
            StateProvinceRepository stateRepo,
            CityRepository cityRepo,
            AddressRepository addressRepo
    ) {
        return args -> {
            Faker faker = new Faker();
            Random random = new Random();

            log.info("start seeding Database ... ");
            // Define Countries and Their Corresponding States
            Map<String, List<String>> countryStatesMap = new HashMap<>();
            countryStatesMap.put("United States", Arrays.asList("California", "Texas", "New York", "Washington", "Florida"));
            countryStatesMap.put("Canada", Arrays.asList("Ontario", "British Columbia", "Quebec", "Alberta", "Manitoba"));
            countryStatesMap.put("Germany", Arrays.asList("Bavaria", "Berlin", "Hamburg", "Saxony", "Hesse"));
            countryStatesMap.put("India", Arrays.asList("Maharashtra", "Karnataka", "Delhi", "Tamil Nadu", "Gujarat"));

            int numCountries = 4;  // Number of countries to generate
            int statesPerCountry = 2; // States per country
            int citiesPerState = 3;
            int addressesPerCity = 50;

            List<Country> savedCountries = new ArrayList<>();

            // 1) Generate Countries from the Predefined List
            List<String> countryNames = new ArrayList<>(countryStatesMap.keySet());
            Collections.shuffle(countryNames);  // Shuffle to pick random countries
            log.info("start generating Countries from the Predefined List ");
            for (int i = 0; i < numCountries; i++) {
                String countryName = countryNames.get(i); // Pick a country from the predefined list
                Country country = new Country(countryName);
                savedCountries.add(countryRepo.save(country));
            }

            // 2) Generate States for Each Country Based on Mapping
            for (Country country : savedCountries) {
                List<StateProvince> savedStates = new ArrayList<>();
                List<String> statesList = countryStatesMap.get(country.getName());

                if (statesList == null || statesList.isEmpty()) {
                    continue; // Skip if no states are found for this country
                }

                Collections.shuffle(statesList); // Shuffle to get random states

                for (int s = 0; s < Math.min(statesPerCountry, statesList.size()); s++) {
                    String stateName = statesList.get(s); // Pick a state from the predefined list
                    StateProvince state = new StateProvince(stateName, country);
                    savedStates.add(stateRepo.save(state));
                }

                // 3) Generate Cities for Each State
                for (StateProvince state : savedStates) {
                    List<City> savedCities = new ArrayList<>();
                    for (int c = 0; c < citiesPerState; c++) {
                        City city = new City(faker.address().cityName(), state);
                        savedCities.add(cityRepo.save(city));
                    }

                    // 4) Generate Addresses for Each City
                    for (City city : savedCities) {
                        for (int a = 0; a < addressesPerCity; a++) {
                            Address address = new Address(
                                    faker.address().streetAddress(),
                                    faker.address().secondaryAddress(),
                                    faker.address().zipCode(),
                                    city
                            );
                            addressRepo.save(address);
                        }
                    }
                }
            }
            log.info("Database seeded with random  {} countries, {} states per country , {} cities per state , and {}" +
                            " addresses per city" ,
                    numCountries ,
                    statesPerCountry ,
                    citiesPerState ,
                    addressesPerCity );

        };
    }
}