package com.thoughtworks.chongzhen.parkinglot.entity.DO;

import com.thoughtworks.chongzhen.parkinglot.entity.Ticket;
import com.thoughtworks.chongzhen.parkinglot.exceptionHanding.exceptions.CarNotFoundException;
import com.thoughtworks.chongzhen.parkinglot.exceptionHanding.exceptions.NoParkingLotException;
import com.thoughtworks.chongzhen.parkinglot.exceptionHanding.exceptions.ParkingBoyNotFoundException;
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
import java.util.stream.Collectors;

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

    public void hireParkingBoy(ParkingBoy parkingBoy) {
        this.getParkingBoys().add(parkingBoy);
    }

    public void fireParkingBoy(String parkingBoyName) {
        List<ParkingBoy> updatedParkingBoys = this.getParkingBoys().stream()
                .filter(parkingBoy -> !validateParkingBoy(parkingBoy, parkingBoyName)).collect(Collectors.toList());
        this.setParkingBoys(updatedParkingBoys);
    }

    public void buildParkingLot(long lotCapacity, String boyName, String lotName) {
        ParkingLot parkingLot = ParkingLot.builder().lotsRemain(lotCapacity).name(lotName).build();
        saveFindLotsByBoyName(boyName).add(parkingLot);
        this.getParkingLots().add(parkingLot);
    }
    //bug
    public void destroyParkingLot(String boyName, ParkingLot parkingLot) {
        this.getParkingBoys().stream().filter(parkingBoy -> parkingBoy.getName().equals(boyName)).findAny()
                .orElseThrow(() -> {
                    throw new ParkingBoyNotFoundException(404, "invalid name", "cannot find parking boy with name" + boyName);
                })
                .getParkingLots().remove(parkingLot);
        this.getParkingLots().remove(parkingLot);
    }

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
                .findAny().orElseThrow(() -> {
                    throw new CarNotFoundException(404, "invalid licence plate", "no such car with plate number " + licencePlate);
                });

        foundParkingLot.getCars().remove(foundCar);
        foundParkingLot.setLotsRemain(foundParkingLot.getLotsRemain() + 1);
        return foundCar;
    }

    private boolean validateParkingBoy(ParkingBoy parkingBoy, String boyName) {
        boolean isEmpty = parkingBoy.getName().equals(boyName) && parkingBoy.getParkingLots().size() != 0;
        if (isEmpty) {
            throw new RuntimeException("cannot delete parking boy when it is not empty");
        }
        return parkingBoy.getName().equals(boyName) && parkingBoy.getParkingLots().size() == 0;
    }

    private List<ParkingLot> saveFindLotsByBoyName(String boyName) {
        return this.getParkingBoys().stream().filter(parkingBoy -> parkingBoy.getName().equals(boyName)).findAny()
                .orElseThrow(() -> {
                    throw new ParkingBoyNotFoundException(404, "invalid name", "cannot find parking boy with name" + boyName);
                }).getParkingLots();
    }
}
