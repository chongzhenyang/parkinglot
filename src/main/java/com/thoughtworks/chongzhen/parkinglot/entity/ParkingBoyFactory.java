package com.thoughtworks.chongzhen.parkinglot.entity;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingBoy;
import org.springframework.stereotype.Component;

@Component
public class ParkingBoyFactory {
    public ParkingBoyObject getParkingBoy(ParkingBoy parkingBoy) {
        return parkingBoy.isSmart() ?
                new SmartParkingBoyObject(parkingBoy.getId(), parkingBoy.isSmart(), parkingBoy.getName(), parkingBoy.getPreviousVisitedLot(), parkingBoy.getParkingLots()) :
                new StupidParkingBoyObject(parkingBoy.getId(), parkingBoy.isSmart(), parkingBoy.getName(), parkingBoy.getPreviousVisitedLot(), parkingBoy.getParkingLots());
    }
}
