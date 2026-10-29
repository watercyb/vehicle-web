package com.blueberryjoy.car_rental_service.service;

import com.blueberryjoy.car_rental_service.model.Status;
import com.blueberryjoy.car_rental_service.model.Vehicle;
import com.blueberryjoy.car_rental_service.repository.VehicleRepositoryInt;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class VehicleService implements VehicleServiceInt {

    private final VehicleRepositoryInt vehicleRepository;

    @Override
    public Vehicle create(Vehicle vehicle) {
        vehicle.setStatus(Status.AVAILABLE);
        vehicle.setOwner(null);
        vehicle.setAssociationDate(null);
        return vehicleRepository.save(vehicle);
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return (List<Vehicle>) vehicleRepository.findAll();
    }

    @Override
    public Vehicle findById(int vehicleId) {
        return vehicleRepository.findById(vehicleId).orElseThrow();
    }

    @Override
    public void associate(int vehicleId, String userId) {
        var vehicle = vehicleRepository.findById(vehicleId)
                .filter(v -> v.getStatus() == Status.AVAILABLE)
                .orElseThrow();
        vehicle.setStatus(Status.ASSOCIATED);
        vehicle.setAssociationDate(new Date());
        vehicle.setOwner(userId);

        vehicleRepository.save(vehicle);
    }

    @Override
    public void removeAssociate(int vehicleId, String userId) {
        var vehicle = vehicleRepository.findById(vehicleId)
                .filter(v -> v.getStatus() == Status.ASSOCIATED)
                .filter(v -> v.getOwner().equals(userId))
                .orElseThrow();
        vehicle.setStatus(Status.AVAILABLE);
        vehicle.setAssociationDate(null);
        vehicle.setOwner(null);

        vehicleRepository.save(vehicle);
    }
}
