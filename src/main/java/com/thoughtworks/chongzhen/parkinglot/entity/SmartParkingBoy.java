package com.thoughtworks.chongzhen.parkinglot.entity;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingLot;
import com.thoughtworks.chongzhen.parkinglot.exceptionHanding.exceptions.NoParkingLotException;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;


@Getter
@Setter
public class SmartParkingBoy extends AbstractParkingBoy {

    public SmartParkingBoy(long id, boolean isSmart, String name, long previousVisitedLot, List<ParkingLot> parkingLots) {
        this.setId(id);
        this.setSmart(isSmart);
        this.setName(name);
        this.setPreviousVisitedLot(previousVisitedLot);
        this.setParkingLots(parkingLots);
    }

    @Override
    public ParkingLot choseParkingLot() {
        return this.getParkingLots().stream()
                .filter(lot -> lot.getLotsRemain() > 0)
                .max(Comparator.comparing(ParkingLot::getLotsRemain)).orElseThrow(() -> {
                    throw new NoParkingLotException(404, "no parking lot found", "cannot find any parking lot");
                });
    }
}
