package com.blueberryjoy.car_rental_service.controller;

import com.blueberryjoy.car_rental_service.model.Vehicle;
import com.blueberryjoy.car_rental_service.service.VehicleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("vehicles")
@AllArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping
    public List<Vehicle> getAll() {
        return vehicleService.getAllVehicles();
    }

    @GetMapping("{vehicleId}")
    public Vehicle getVehicle(@PathVariable int vehicleId) {
        return vehicleService.findById(vehicleId);
    }

    @PostMapping
    public Vehicle create(@Valid @RequestBody Vehicle vehicle) {
        return vehicleService.create(vehicle);
    }

    @PostMapping("{vehicleId}/user/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void associate(@PathVariable int vehicleId, @PathVariable String userId) {
        vehicleService.associate(vehicleId, userId);
    }

    @DeleteMapping("{vehicleId}/user/{userId}")
    public void removeAssociate(@PathVariable int vehicleId, @PathVariable String userId) {
        vehicleService.removeAssociate(vehicleId, userId);
    }
}
