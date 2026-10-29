package com.blueberryjoy.gateway.proxy;

import com.blueberryjoy.gateway.model.Vehicle;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Data
@Component
public class VehicleProxy {

    private final RestTemplate restTemplate;
    private final String url;

    public VehicleProxy(@Value("${url.rental}") String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    public Vehicle[] getAllVehicles() {
        return restTemplate.getForObject(url + "vehicles", Vehicle[].class);
    }

    public Vehicle getVehicle(String vehicleId) {
        return restTemplate.getForObject(url + "vehicles/{vehicleId}", Vehicle.class, Map.of("vehicleId", vehicleId));
    }

    public Vehicle create(Vehicle vehicle) {
        return restTemplate.postForObject(url + "vehicles", vehicle, Vehicle.class);
    }

    public void associate(int vehicleId, String userId) {
        restTemplate.postForObject(url + "vehicles/{vehicleId}/user/{userId}", null, Void.class, Map.of("vehicleId", vehicleId, "userId", userId));
    }

    public void removeAssociate(int vehicleId, String userId) {
        restTemplate.delete(url + "vehicles/{vehicleId}/user/{userId}", Map.of("vehicleId", vehicleId, "userId", userId));
    }
}
