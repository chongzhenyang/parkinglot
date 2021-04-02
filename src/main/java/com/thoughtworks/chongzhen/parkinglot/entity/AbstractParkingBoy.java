package com.thoughtworks.chongzhen.parkinglot.entity;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.Car;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingLot;
import com.thoughtworks.chongzhen.parkinglot.exceptionHanding.exceptions.CarNotFoundException;
import com.thoughtworks.chongzhen.parkinglot.exceptionHanding.exceptions.NoParkingLotException;
import lombok.Getter;
import lombok.Setter;

import java.util.Base64;
import java.util.List;

@Setter
@Getter
public abstract class AbstractParkingBoy {

    private long id;

    private boolean isSmart;

    private String name;

    private long previousVisitedLot;

    List<ParkingLot> parkingLots;

    public abstract ParkingLot choseParkingLot();

    public long[] park(Car car) {
        ParkingLot selectedParkingLot = choseParkingLot();
        List<Car> carList = selectedParkingLot.getCars();
        carList.add(car);
        selectedParkingLot.setLotsRemain(selectedParkingLot.getLotsRemain() - 1);
        this.setPreviousVisitedLot(selectedParkingLot.getId());

        long lotId = selectedParkingLot.getId();

        return new long[]{this.getId(), lotId};
    }

    public Car pickUp(Ticket ticket) {
        long lotId = ticket.getParkingLotId();
        String licencePlate = new String(Base64.getDecoder().decode(ticket.getTicketNumber()));
        ParkingLot selectParkingLot = this.getParkingLots().stream()
                .filter(parkingLot -> parkingLot.getId() == lotId)
                .findAny().orElseThrow(() -> {
                    throw new NoParkingLotException(404, "invalid ticket", "cannot find parking lot with id" + ticket.getParkingLotId());
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

    protected boolean roundRobin(ParkingLot parkingLot, StupidParkingBoy stupidParkingBoy) {
        if (isFirstLotGivenPreviousIsLastLot(parkingLot, stupidParkingBoy)) {
            return true;
        }
        return parkingLot.getLotsRemain() > 0 && parkingLot.getId() != stupidParkingBoy.getPreviousVisitedLot();
    }

    protected boolean isFirstLotGivenPreviousIsLastLot(ParkingLot parkingLot, StupidParkingBoy stupidParkingBoy) {
        long firstId = stupidParkingBoy.getParkingLots().stream().findFirst().orElseThrow(() -> {
            throw new NoParkingLotException(404, "no parking lot found", "empty parking lot list");
        }).getId();

        long lastId = firstId + stupidParkingBoy.getParkingLots().size();

        return stupidParkingBoy.getPreviousVisitedLot() == lastId && parkingLot.getId() == firstId;
    }
}
