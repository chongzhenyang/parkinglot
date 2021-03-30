package com.thoughtworks.chongzhen.parkinglot.entity;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.Car;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingLot;
import com.thoughtworks.chongzhen.parkinglot.exceptionHanding.exceptions.CarNotFoundException;
import com.thoughtworks.chongzhen.parkinglot.exceptionHanding.exceptions.NoParkingLotException;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;


@Data
@AllArgsConstructor
public class StupidParkingBoyObject implements ParkingBoyObject {

    private long id;

    private boolean isSmart;

    private String name;

    private long previousVisitedLot;

    List<ParkingLot> parkingLots;

    @Override
    public TicketObject park(Car car) {
        ParkingLot selectedParkingLot = this.getParkingLots().stream().
                filter(parkingLot -> roundRobin(parkingLot, this))
                .findAny().orElseThrow(() -> {
                    throw new NoParkingLotException(404, "not found", "cannot find any parking lot");
                });
        // todo:
        selectedParkingLot.getCars().add(car);
        selectedParkingLot.setLotsRemain(selectedParkingLot.getLotsRemain() - 1);
        this.setPreviousVisitedLot(selectedParkingLot.getId());


        String plateNumberEncoded = Base64.getEncoder()
                .encodeToString(car.getLicencePlate().getBytes(StandardCharsets.UTF_8));
        long lotId = selectedParkingLot.getId();

        return TicketObject.builder()
                .ticketNumber(plateNumberEncoded)
                .parkingLotId(lotId)
                .parkingBoyId(this.getId())
                .build();
    }

    @Override
    public Car pickUp(TicketObject ticketObject) {
        long lotId = ticketObject.getParkingLotId();
        String licencePlate = new String(Base64.getDecoder().decode(ticketObject.getTicketNumber()));
        ParkingLot selectParkingLot = this.getParkingLots().stream()
                .filter(parkingLot -> parkingLot.getId() == lotId)
                .findAny().orElseThrow(() -> {
                    throw new NoParkingLotException(404, "invalid ticket", "cannot find parking lot with id" + ticketObject.getParkingLotId());
                });

        Car foundCar = selectParkingLot.getCars().stream()
                .filter(car -> car.getLicencePlate().equals(licencePlate))
                .findAny().orElseThrow(() -> {
                    throw new CarNotFoundException(404, "invalid licence plate", "cannot find car with licence plate number" + licencePlate);
                });

        selectParkingLot.getCars().remove(foundCar);
        selectParkingLot.setLotsRemain(selectParkingLot.getLotsRemain() + 1);

        return foundCar;
    }

    private boolean roundRobin(ParkingLot parkingLot, StupidParkingBoyObject stupidParkingBoy) {
        if (isFirstLotGivenPreviousIsLastLot(parkingLot, stupidParkingBoy)) {
            return true;
        }
        return parkingLot.getLotsRemain() > 0 && parkingLot.getId() != stupidParkingBoy.getPreviousVisitedLot();
    }

    private boolean isFirstLotGivenPreviousIsLastLot(ParkingLot parkingLot, StupidParkingBoyObject stupidParkingBoy) {
        long firstId = stupidParkingBoy.getParkingLots().stream().findFirst().orElseThrow(() -> {
            throw new NoParkingLotException(404, "no parking lot found", "sa");
        }).getId();

        long lastId = firstId + stupidParkingBoy.getParkingLots().size();

        return stupidParkingBoy.getPreviousVisitedLot() == lastId && parkingLot.getId() == firstId;
    }
}
