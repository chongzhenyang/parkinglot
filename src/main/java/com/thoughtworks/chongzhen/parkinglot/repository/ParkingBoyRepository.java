package com.thoughtworks.chongzhen.parkinglot.repository;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingBoy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingBoyRepository extends JpaRepository<ParkingBoy, Long> {

    @Query(nativeQuery=true, value="SELECT * FROM parking_boy ORDER BY rand() LIMIT 1")
    ParkingBoy findRandomParkingBoy();
}
