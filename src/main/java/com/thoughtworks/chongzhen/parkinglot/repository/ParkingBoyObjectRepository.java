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

    public AbstractParkingBoy findRandomParkingBoyObject() {

        ParkingBoy parkingBoy = parkingBoyRepository.findRandomParkingBoy();

        return ParkingBoyFactory.getParkingBoy(parkingBoy);
    }

    public AbstractParkingBoy findParkingBoyObjectByTicket(Ticket ticket) {
        ParkingBoy parkingBoy = parkingBoyRepository.findById(ticket.getParkingBoyId())
                .orElseThrow(() -> new ParkingBoyNotFoundException(404, "invalid id", "cannot find parking boy with id " + ticket.getParkingBoyId()));

        return ParkingBoyFactory.getParkingBoy(parkingBoy);
    }

    public ParkingBoy save(AbstractParkingBoy abstractParkingBoy) {
        ParkingBoy parkingBoy = ParkingBoy.builder()
                .id(abstractParkingBoy.getId())
                .isSmart(abstractParkingBoy.isSmart())
                .name(abstractParkingBoy.getName())
                .previousVisitedLot(abstractParkingBoy.getPreviousVisitedLot())
                .parkingLots(abstractParkingBoy.getParkingLots())
                .build();

        return parkingBoyRepository.save(parkingBoy);
    }
}
