package edu.seattleu.addressmanager.service;

import edu.seattleu.addressmanager.metrics.CustomMetrics;
import edu.seattleu.addressmanager.model.Address;
import edu.seattleu.addressmanager.repository.AddressRepository;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AddressService {
    private final AddressRepository addressRepo;
    private final CustomMetrics customMetrics;

    public AddressService(AddressRepository addressRepo, CustomMetrics customMetrics) {
        this.addressRepo = addressRepo;
        this.customMetrics = customMetrics;
    }

    public List<Address> findAll() {
        log.info("Fetching all addresses...");
        customMetrics.incrementAddressOps(); // increment metric
        return addressRepo.findAll();
    }

    public Optional<Address> findById(Long id) {
        log.info("Finding address by ID = {}", id);
        customMetrics.incrementAddressOps();
        return addressRepo.findById(id);
    }

    public Address save(Address address) {
        log.info("Saving address: {}", address);
        customMetrics.incrementAddressOps();
        return addressRepo.save(address);
    }

    public void delete(Long id) {
        log.info("Deleting address with ID = {}", id);
        customMetrics.incrementAddressOps();
        addressRepo.deleteById(id);
    }

    public List<Address> searchByStreet(String street) {
        log.info("Searching addresses by partial street: {}", street);
        customMetrics.incrementAddressOps();
        return addressRepo.findByStreetContainingIgnoreCase(street);
    }

    public List<Address> findByCity(Long cityId) {
        log.info("Fetching addresses in city ID = {}", cityId);
        customMetrics.incrementAddressOps();
        return addressRepo.findByCity_Id(cityId);
    }

    public List<Address> findByState(Long stateId) {
        log.info("Fetching addresses in State ID = {}", stateId);
        customMetrics.incrementAddressOps();
        return addressRepo.findByCity_State_Id(stateId);
    }
}