package com.blueberryjoy.gateway.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Vehicle {

    @JsonProperty("licence_number")
    private String licencePlateNumber;
    private String model;
    private String brand;

    private Status status;
    private String owner;
    @JsonProperty("association_date")
    private Date associationDate;
}
