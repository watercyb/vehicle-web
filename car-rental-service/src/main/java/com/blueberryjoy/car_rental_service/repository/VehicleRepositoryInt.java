package com.blueberryjoy.car_rental_service.repository;

import com.blueberryjoy.car_rental_service.model.Vehicle;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface VehicleRepositoryInt extends CrudRepository<Vehicle, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE Vehicle f SET f.id = f.id + 1 WHERE f.id >= :startPos")
    void shiftPositionsFrom(@Param("startPos") int startPos);
}
