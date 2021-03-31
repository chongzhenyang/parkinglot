package com.thoughtworks.chongzhen.parkinglot.repository;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingManager;
import com.thoughtworks.chongzhen.parkinglot.entity.ParkingManagerObject;
import com.thoughtworks.chongzhen.parkinglot.exceptionHanding.exceptions.ParkingManagerNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ParkingManagerObjectRepository {
    private final ParkingManagerRepository parkingManagerRepository;

    public ParkingManagerObject findParkingManagerObjectById(long id){
        ParkingManager parkingManager = parkingManagerRepository.findById(id).orElseThrow(()->{
            throw new ParkingManagerNotFoundException(404, "invalid id", "cannot find parking manager with id " + id);
        });
        return ParkingManagerObject.init(parkingManager);
    }

    public ParkingManager save(ParkingManagerObject parkingManagerObject){
        ParkingManager parkingManager = ParkingManager.builder()
                .id(parkingManagerObject.getId())
                .name(parkingManagerObject.getName())
                .parkingBoys(parkingManagerObject.getParkingBoys())
                .parkingLots(parkingManagerObject.getParkingLots())
                .build();

        return parkingManagerRepository.save(parkingManager);
    }
}
