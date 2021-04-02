package com.thoughtworks.chongzhen.parkinglot.entity;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingBoy;
import org.springframework.stereotype.Component;

@Component
public class ParkingBoyFactory {
    public static AbstractParkingBoy getParkingBoy(ParkingBoy parkingBoy) {
        return parkingBoy.isSmart() ?
                new SmartParkingBoy(parkingBoy.getId(), parkingBoy.isSmart(), parkingBoy.getName(), parkingBoy.getPreviousVisitedLot(), parkingBoy.getParkingLots()) :
                new StupidParkingBoy(parkingBoy.getId(), parkingBoy.isSmart(), parkingBoy.getName(), parkingBoy.getPreviousVisitedLot(), parkingBoy.getParkingLots());
    }
}
