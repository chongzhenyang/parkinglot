package com.thoughtworks.chongzhen.parkinglot.entity;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.Car;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingBoy;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingLot;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingManager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Random;

@Data
@Builder
@AllArgsConstructor
public class ParkingManagerObject {
    private long id;

    private String name;

    List<ParkingBoy> parkingBoys;

    List<ParkingLot> parkingLots;

    public static ParkingManagerObject init(ParkingManager parkingManager){
        return ParkingManagerObject.builder()
                .id(parkingManager.getId())
                .name(parkingManager.getName())
                .parkingBoys(parkingManager.getParkingBoys())
                .parkingLots(parkingManager.getParkingLots())
                .build();
    }

    public TicketObject park(Car car){
        int randomIndex = new Random().nextInt(parkingLots.size());
        ParkingLot selectedParkingLot = this.getParkingLots().get(randomIndex);
        selectedParkingLot.getCars().add(car);
        selectedParkingLot.setLotsRemain(selectedParkingLot.getLotsRemain() - 1);

        String plateNumberEncoded = Base64.getEncoder()
                .encodeToString(car.getLicencePlate().getBytes(StandardCharsets.UTF_8));
        long lotId = selectedParkingLot.getId();

        return TicketObject.builder()
                .ticketNumber(plateNumberEncoded)
                .parkingLotId(lotId)
                .parkingBoyId(this.getId())
                .build();
    }
}
