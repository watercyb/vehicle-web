package com.blueberryjoy.gateway.controllers;

import com.blueberryjoy.gateway.model.Vehicle;
import com.blueberryjoy.gateway.proxy.VehicleProxy;
import com.blueberryjoy.gateway.util.UserContext;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/vehicles")
@AllArgsConstructor
public class GatewayVehicleController {

    private final VehicleProxy vehicleProxy;
    private final ObjectFactory<UserContext> userContext;

    @GetMapping
    public Vehicle[] getAll() {
        return vehicleProxy.getAllVehicles();
    }

    @GetMapping("{vehicleId}")
    public Vehicle getVehicle(@PathVariable String vehicleId) {
        return vehicleProxy.getVehicle(vehicleId);
    }

    @PostMapping
    public Vehicle create(@RequestBody Vehicle vehicle) {
        userContext.getObject().isAdmin();
        return vehicleProxy.create(vehicle);
    }

    @PostMapping("{vehicleId}/user/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void associate(@PathVariable int vehicleId, @PathVariable String userId) {
        vehicleProxy.associate(vehicleId, userId);
    }

    @DeleteMapping("{vehicleId}/user/{userId}")
    public void removeAssociate(@PathVariable int vehicleId, @PathVariable String userId) {
        vehicleProxy.removeAssociate(vehicleId, userId);
    }
}
