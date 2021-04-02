package com.thoughtworks.chongzhen.parkinglot.entity;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingLot;
import com.thoughtworks.chongzhen.parkinglot.exceptionHanding.exceptions.NoParkingLotException;
import lombok.*;

import java.util.List;


@Getter
@Setter
public class StupidParkingBoy extends AbstractParkingBoy {

    public StupidParkingBoy(long id, boolean isSmart, String name, long previousVisitedLot, List<ParkingLot> parkingLots) {
        this.setId(id);
        this.setSmart(isSmart);
        this.setName(name);
        this.setPreviousVisitedLot(previousVisitedLot);
        this.setParkingLots(parkingLots);
    }

    @Override
    public ParkingLot choseParkingLot() {
        return this.getParkingLots().stream().
                filter(parkingLot -> roundRobin(parkingLot, this))
                .findAny().orElseThrow(() -> {
                    throw new NoParkingLotException(404, "not found", "cannot find any parking lot");
                });
    }
}
