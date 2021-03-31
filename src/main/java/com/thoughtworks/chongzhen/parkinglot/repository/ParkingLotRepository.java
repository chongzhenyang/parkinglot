package com.thoughtworks.chongzhen.parkinglot.repository;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
    ParkingLot findParkingLotByName(String name);

    @Query(nativeQuery=true, value="SELECT * FROM parking_lot ORDER BY rand() LIMIT 1")
    ParkingLot findRandomParkingLot();
}
