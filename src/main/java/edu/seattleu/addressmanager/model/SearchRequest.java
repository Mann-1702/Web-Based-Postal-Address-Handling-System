package edu.seattleu.addressmanager.model;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "SearchRequest", description = "Represents address Search request")
public class SearchRequest {

    @Schema(description = "partial address Request", example = "123 Main St")
    private String street;

    // Constructors, getters, setters
    public SearchRequest() {}

    public SearchRequest(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}