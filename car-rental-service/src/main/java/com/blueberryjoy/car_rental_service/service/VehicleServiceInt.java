package com.blueberryjoy.car_rental_service.service;

import com.blueberryjoy.car_rental_service.model.Vehicle;

import java.util.List;

public interface VehicleServiceInt {

    Vehicle create(Vehicle vehicle);

    List<Vehicle> getAllVehicles();

    Vehicle findById(int vehicleId);

    void associate(int vehicleId, String userId);

    void removeAssociate(int vehicleId, String userId);
}
