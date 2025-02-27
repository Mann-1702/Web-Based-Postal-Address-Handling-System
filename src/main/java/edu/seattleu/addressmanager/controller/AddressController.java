package edu.seattleu.addressmanager.controller;


import edu.seattleu.addressmanager.exceptions.ResourceNotFoundException;
import edu.seattleu.addressmanager.model.Address;
import edu.seattleu.addressmanager.model.SearchRequest;
import edu.seattleu.addressmanager.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/addresses")
@Tag(name = "Addresses", description = "Manage addresses in DynamoDB")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @Operation(summary = "Get all addresses", description = "Retrieve all stored addresses")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of addresses retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<Address> getAll() {
        return addressService.findAll();
    }

    @Operation(summary = "Get address by ID")
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    public ResponseEntity<Address> getById(@PathVariable Long id) {
        return addressService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     *  Search addresses by partial street
     */
    @Operation(summary = "Search addresses by partial address01")
    @GetMapping("/searchByAddress01")
    public  ResponseEntity<List<Address>>  searchByStreet(@Valid @RequestBody SearchRequest request) {
        List<Address> addresses = addressService.searchByStreet(request.getAddress01());

        if (addresses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 when no results
        }

        return ResponseEntity.ok(addresses);
    }

    @Operation(summary = "Search addresses by any")
    @GetMapping("/search")
    public  ResponseEntity<List<Address>>  search(@Valid @RequestBody SearchRequest request) {


        List<Address> addresses = addressService.search(request.getAddress01(),
                request.getAddress02(),
                request.getPostalCode(),
                request.getCityId(),
                request.getStateId(),
                request.getCountryId());

        if (addresses.isEmpty()) {
            // Instead of returning 404 directly:
            throw new ResourceNotFoundException("No addresses found for your search criteria.");
        }
        return ResponseEntity.ok(addresses);
    }


    @Operation(summary = "Get addresses by city ID")
    @GetMapping("/city/{cityId}")
    public List<Address> addressesByCity(@PathVariable Long cityId) {
        return addressService.findByCity(cityId);
    }

    @Operation(summary = "Get addresses by state ID")
    @GetMapping("/state/{stateId}")
    public List<Address> addressesByState(@PathVariable Long stateId) {
        return addressService.findByState(stateId);
    }




}
