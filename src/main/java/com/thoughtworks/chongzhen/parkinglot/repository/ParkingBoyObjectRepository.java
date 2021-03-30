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

    private final ParkingBoyFactory parkingBoyFactory;


    public ParkingBoyObject findRandomParkingBoyObject() {

        ParkingBoy parkingBoy = parkingBoyRepository.findRandomParkingBoy();

        return parkingBoyFactory.getParkingBoy(parkingBoy);
    }

    public ParkingBoyObject findParkingBoyObjectByTicket(TicketObject ticketObject) {
        ParkingBoy parkingBoy = parkingBoyRepository.findById(ticketObject.getParkingBoyId())
                .orElseThrow(() -> new ParkingBoyNotFoundException(404, "invalid id", "cannot find parking boy with id " + ticketObject.getParkingBoyId()));

        return parkingBoyFactory.getParkingBoy(parkingBoy);
    }

    public ParkingBoy save(ParkingBoyObject parkingBoyObject) {
        ParkingBoy parkingBoy = null;
        if (parkingBoyObject instanceof SmartParkingBoyObject) {
            SmartParkingBoyObject smartParkingBoyObject = (SmartParkingBoyObject) parkingBoyObject;
            parkingBoy = ParkingBoy.builder()
                    .id(smartParkingBoyObject.getId())
                    .isSmart(smartParkingBoyObject.isSmart())
                    .name(smartParkingBoyObject.getName())
                    .previousVisitedLot(smartParkingBoyObject.getPreviousVisitedLot())
                    .parkingLots(smartParkingBoyObject.getParkingLots()).build();
        } else if (parkingBoyObject instanceof StupidParkingBoyObject) {
            StupidParkingBoyObject stupidParkingBoyObject = (StupidParkingBoyObject) parkingBoyObject;
            parkingBoy = ParkingBoy.builder()
                    .id(stupidParkingBoyObject.getId())
                    .isSmart(stupidParkingBoyObject.isSmart())
                    .name(stupidParkingBoyObject.getName())
                    .previousVisitedLot(stupidParkingBoyObject.getPreviousVisitedLot())
                    .parkingLots(stupidParkingBoyObject.getParkingLots()).build();
        }

        return parkingBoyRepository.save(parkingBoy);
    }
}
