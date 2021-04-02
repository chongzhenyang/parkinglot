package com.thoughtworks.chongzhen.parkinglot.service;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.*;
import com.thoughtworks.chongzhen.parkinglot.entity.Ticket;
import com.thoughtworks.chongzhen.parkinglot.exceptionHanding.exceptions.ParkingBoyNotFoundException;
import com.thoughtworks.chongzhen.parkinglot.exceptionHanding.exceptions.ParkingManagerNotFoundException;
import com.thoughtworks.chongzhen.parkinglot.repository.ParkingLotRepository;
import com.thoughtworks.chongzhen.parkinglot.repository.ParkingManagerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ParkingManagerService {
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingManagerRepository parkingManagerRepository;

    public List<ParkingManager> getAll() {
        return parkingManagerRepository.findAll();
    }

    public ParkingManager createParkingManager(ParkingManager parkingManager) {
        return parkingManagerRepository.save(parkingManager);
    }

    //TODO: DDD should be inside parkingManager object
    public ParkingManager createParkingLot(long lotCapacity, long managerId, String boyName, String lotName) {
        ParkingLot parkingLot = ParkingLot.builder().lotsRemain(lotCapacity).name(lotName).build();
        ParkingManager parkingManager = saveFindManagerById(managerId);

        saveFindLotsByBoyName(parkingManager, boyName).add(parkingLot);
        parkingManager.getParkingLots().add(parkingLot);

        return parkingManagerRepository.save(parkingManager);
    }

    //TODO: DDD should be inside parkingManager object
    public ParkingManager deleteParkingLot(long managerId, String boyName, String parkingLotName) {
        ParkingManager parkingManager = saveFindManagerById(managerId);
        ParkingLot parkingLot = parkingLotRepository.findParkingLotByName(parkingLotName)
                .filter(lot -> lot.getLotsRemain() != 500)
                .orElseThrow(() -> {
                    throw new RuntimeException("cannot delete this parking lot");
                });
        saveFindLotsByBoyName(parkingManager, boyName).remove(parkingLot);
        parkingManager.getParkingLots().remove(parkingLot);
        return parkingManagerRepository.save(parkingManager);
    }

    //TODO: DDD should be inside parkingManager object
    public ParkingManager createParkingBoy(long managerId, ParkingBoy parkingBoy) {
        ParkingManager parkingManager = saveFindManagerById(managerId);

        parkingManager.getParkingBoys().add(parkingBoy);
        return parkingManagerRepository.save(parkingManager);
    }

    //TODO: DDD should be inside parkingManager object
    public ParkingManager deleteParkingBoy(long manageId, String boyName) {
        ParkingManager parkingManager = saveFindManagerById(manageId);
        List<ParkingBoy> parkingBoys = parkingManager.getParkingBoys().stream()
                .filter(parkingBoy -> !validateParkingBoy(parkingBoy, boyName)).collect(Collectors.toList());
        parkingManager.setParkingBoys(parkingBoys);
        return parkingManagerRepository.save(parkingManager);
    }

    public Ticket park(Car car, long managerId) {
        ParkingManager parkingManager = parkingManagerRepository.findById(managerId).orElseThrow(() -> {
                    throw new ParkingManagerNotFoundException(404, "invalid id", "cannot find parking manager with id" + managerId);
                });
        Ticket ticket = parkingManager.park(car);
        parkingManagerRepository.save(parkingManager);
        return ticket;
    }

    public Car pickUp(Ticket ticket) {
        ParkingManager parkingManager = parkingManagerRepository.findById(ticket.getParkingBoyId()).orElseThrow(() -> {
            throw new ParkingManagerNotFoundException(404, "invalid id", "cannot find parking manager with id" + ticket.getParkingBoyId());
        });
        Car foundCar = parkingManager.pickUp(ticket);
        parkingManagerRepository.save(parkingManager);

        return foundCar;
    }

    //TODO: DDD should be inside parkingManager object
    private ParkingManager saveFindManagerById(long managerId) {
        return parkingManagerRepository.findById(managerId).orElseThrow(() -> {
            throw new ParkingManagerNotFoundException(404, "invalid id", "cannot find parking manager with id" + managerId);
        });
    }

    //TODO: DDD should be inside parkingManager object
    private List<ParkingLot> saveFindLotsByBoyName(ParkingManager parkingManager, String boyName) {
        return parkingManager.getParkingBoys().stream().filter(parkingBoy -> parkingBoy.getName().equals(boyName)).findAny()
                .orElseThrow(() -> {
                    throw new ParkingBoyNotFoundException(404, "invalid name", "cannot find parking boy with name" + boyName);
                })
                .getParkingLots();
    }

    private boolean validateParkingBoy(ParkingBoy parkingBoy, String boyName) {
        boolean isEmpty = parkingBoy.getName().equals(boyName) && parkingBoy.getParkingLots().size() == 0;
        if (isEmpty) {
            throw new RuntimeException("cannot delete parking boy when it is not empty");
        }
        return parkingBoy.getName().equals(boyName) && parkingBoy.getParkingLots().size() == 0;
    }
}
