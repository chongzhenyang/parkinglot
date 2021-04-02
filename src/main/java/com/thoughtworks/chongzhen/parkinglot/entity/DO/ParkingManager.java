package com.thoughtworks.chongzhen.parkinglot.entity.DO;

import com.thoughtworks.chongzhen.parkinglot.entity.Ticket;
import com.thoughtworks.chongzhen.parkinglot.exceptionHanding.exceptions.CarNotFoundException;
import com.thoughtworks.chongzhen.parkinglot.exceptionHanding.exceptions.NoParkingLotException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingManager {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "parkingManager_id")
    List<ParkingBoy> parkingBoys = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "parkingManager_id")
    List<ParkingLot> parkingLots = new ArrayList<>();

    public Ticket park(Car car) {
        int randomIndex = new Random().nextInt(parkingLots.size());
        ParkingLot selectedParkingLot = this.getParkingLots().get(randomIndex);
        selectedParkingLot.getCars().add(car);
        selectedParkingLot.setLotsRemain(selectedParkingLot.getLotsRemain() - 1);

        String plateNumberEncoded = Base64.getEncoder()
                .encodeToString(car.getLicencePlate().getBytes(StandardCharsets.UTF_8));
        long lotId = selectedParkingLot.getId();

        return Ticket.builder()
                .ticketNumber(plateNumberEncoded)
                .parkingLotId(lotId)
                .parkingBoyId(this.getId())
                .isParkedByManager(true)
                .build();
    }

    public Car pickUp(Ticket ticket) {
        long lotId = ticket.getParkingLotId();
        String licencePlate = new String(Base64.getDecoder().decode(ticket.getTicketNumber()));
        ParkingLot foundParkingLot = this.getParkingLots().stream().filter(parkingLot ->
                parkingLot.getId() == lotId
        ).findAny().orElseThrow(() -> {
            throw new NoParkingLotException(404, "invalid lot id", "cannot find parking lot with id " + lotId);
        });

        Car foundCar = foundParkingLot.getCars().stream().filter(car -> car.getLicencePlate().equals(licencePlate))
                .findAny().orElseThrow(()->{throw new CarNotFoundException(404, "invalid licence plate", "no such car with plate number " + licencePlate);
                });

        foundParkingLot.getCars().remove(foundCar);
        foundParkingLot.setLotsRemain(foundParkingLot.getLotsRemain() + 1);
        return foundCar;
    }
}
