package com.blueberryjoy.car_rental_service.model;

import com.blueberryjoy.car_rental_service.constants.AppConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
//Use class name by default.
//@Table(name = "Vehicle")
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = AppConstants.LICENCE_PLATE_NUMBER_MANDATORY)
    @Column(length = 50)
    @JsonProperty("licence_number")
    private String licencePlateNumber;

    @NotBlank(message = AppConstants.MODEL_MANDATORY)
    @Column(length = 50)
    private String model;

    @NotBlank(message = AppConstants.BRAND_MANDATORY)
    @Column(length = 50)
    private String brand;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private Status status;

    @Column(length = 50)
    private String owner;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(length = 30)
    @JsonProperty("association_date")
    private Date associationDate;
}
