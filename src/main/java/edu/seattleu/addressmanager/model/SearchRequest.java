package edu.seattleu.addressmanager.model;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "SearchRequest", description = "Represents address Search request")
public class SearchRequest {

    @Schema(description = "partial address Request by address01", example = "123 Main St")
    private String address01;

    // Constructors, getters, setters
    public SearchRequest() {}

    public SearchRequest(String street) {
        this.address01 = street;
    }

    public String getAddress01() {
        return address01;
    }

    public void setAddress01(String address01) {
        this.address01 = address01;
    }
}