package edu.seattleu.addressmanager.controller;


import edu.seattleu.addressmanager.model.Address;
import edu.seattleu.addressmanager.model.SearchRequest;
import edu.seattleu.addressmanager.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Operation(summary = "Search addresses by partial street")
    @GetMapping("/search")
    public  ResponseEntity<List<Address>>  searchByStreet(@RequestBody SearchRequest request) {
        List<Address> addresses = addressService.searchByStreet(request.getStreet());

        if (addresses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 when no results
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


    /*
 @Operation(summary = "Create or update address")
 @PostMapping
 @ApiResponses(value = {
         @ApiResponse(responseCode = "201", description = "Address created successfully"),
         @ApiResponse(responseCode = "400", description = "Invalid input data")
 })
 public Address createOrUpdate(@RequestBody Address address) {
     return addressService.save(address);
 }


 @Operation(summary = "Delete address")
 @DeleteMapping("/{id}")
 public ResponseEntity<Void> delete(@PathVariable Long id) {
     addressService.delete(id);
     return ResponseEntity.noContent().build();
 }


  */


}
