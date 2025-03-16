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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/addresses")
@Tag(name = "Addresses", description = "Manage addresses in DynamoDB")
@CrossOrigin(origins = "http://localhost:3000")
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
    public  ResponseEntity<Page<Address>>  searchByStreet(@Valid @RequestBody SearchRequest request, Pageable pageable) {
        Page<Address> addresses = addressService.searchByStreet(request.getAddress01(), pageable);

        if (addresses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 when no results
        }

        return ResponseEntity.ok(addresses);
    }



    @Operation(summary = "Search addresses by any combination , you must at least give one value")
    @PostMapping("/search")
    public  ResponseEntity<Page<Address>>  search(@Valid @RequestBody SearchRequest request, Pageable pageable) {


        Page<Address> addresses = addressService.search(request.getAddress01(),
                request.getAddress02(),
                request.getPostalCode(),
                request.getCityId(),
                request.getStateId(),
                request.getCountryId(),
                pageable);

        if (addresses.isEmpty()) {
            // Instead of returning 404 directly:
            throw new ResourceNotFoundException("No addresses found for your search criteria.");
        }
        return ResponseEntity.ok(addresses);
    }


    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(
            @RequestParam(required = false) String address01,
            @RequestParam(required = false) String address02,
            @RequestParam(required = false) String postalCode,
            @RequestParam(required = false) Long cityId,
            @RequestParam(required = false) Long stateId,
            @RequestParam(required = false) Long countryId,
            Pageable pageable) {

        // Call your existing service method (addressService.search) with the extracted params
        Page<Address> addresses = addressService.search(
                address01,
                address02,
                postalCode,
                cityId,
                stateId,
                countryId,
                pageable
        );

        if (addresses.isEmpty()) {
            throw new ResourceNotFoundException("No addresses found for your search criteria.");
        }

        // Convert Page<Address> to Map<String, Object> to match frontend expectations
        Map<String, Object> response = new HashMap<>();
        response.put("totalPages", addresses.getTotalPages());
        response.put("totalElements", addresses.getTotalElements());
        response.put("page", pageable.getPageNumber() + 1);  // to make it 1-based
        response.put("content", addresses.getContent());

        return ResponseEntity.ok(response);
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
