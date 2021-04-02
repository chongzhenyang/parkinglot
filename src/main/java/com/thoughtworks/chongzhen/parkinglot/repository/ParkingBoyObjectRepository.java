package com.thoughtworks.chongzhen.parkinglot.repository;

import com.thoughtworks.chongzhen.parkinglot.entity.*;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingBoy;
import com.thoughtworks.chongzhen.parkinglot.exceptionHanding.exceptions.ParkingBoyNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class ParkingBoyObjectRepository {
    private final ParkingBoyRepository parkingBoyRepository;

    public ParkingBoyObject findRandomParkingBoyObject() {

        ParkingBoy parkingBoy = parkingBoyRepository.findRandomParkingBoy();

        return ParkingBoyFactory.getParkingBoy(parkingBoy);
    }

    public ParkingBoyObject findParkingBoyObjectByTicket(TicketObject ticketObject) {
        ParkingBoy parkingBoy = parkingBoyRepository.findById(ticketObject.getParkingBoyId())
                .orElseThrow(() -> new ParkingBoyNotFoundException(404, "invalid id", "cannot find parking boy with id " + ticketObject.getParkingBoyId()));

        return ParkingBoyFactory.getParkingBoy(parkingBoy);
    }

    public ParkingBoy save(ParkingBoyObject parkingBoyObject) {
        ParkingBoy parkingBoy = ParkingBoy.builder()
                .id(parkingBoyObject.getId())
                .isSmart(parkingBoyObject.isSmart())
                .name(parkingBoyObject.getName())
                .previousVisitedLot(parkingBoyObject.getPreviousVisitedLot())
                .parkingLots(parkingBoyObject.getParkingLots())
                .build();

        return parkingBoyRepository.save(parkingBoy);
    }
}
