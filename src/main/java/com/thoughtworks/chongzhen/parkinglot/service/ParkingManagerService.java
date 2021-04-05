package com.thoughtworks.chongzhen.parkinglot.service;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.*;
import com.thoughtworks.chongzhen.parkinglot.entity.Ticket;
import com.thoughtworks.chongzhen.parkinglot.exceptionHanding.exceptions.ParkingManagerNotFoundException;
import com.thoughtworks.chongzhen.parkinglot.repository.ParkingLotRepository;
import com.thoughtworks.chongzhen.parkinglot.repository.ParkingManagerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


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

    public ParkingManager createParkingLot(long lotCapacity, long managerId, String boyName, String lotName) {
        ParkingManager parkingManager = saveFindManagerById(managerId);
        parkingManager.buildParkingLot(lotCapacity, boyName, lotName);

        return parkingManagerRepository.save(parkingManager);
    }

    public ParkingManager deleteParkingLot(long managerId, String boyName, String parkingLotName) {
        ParkingManager parkingManager = saveFindManagerById(managerId);
        ParkingLot parkingLot = parkingLotRepository.findParkingLotByName(parkingLotName)
                .filter(lot -> lot.getLotsRemain() == 500)
                .orElseThrow(() -> {
                    throw new RuntimeException("cannot delete this parking lot");
                });
        parkingManager.destroyParkingLot(boyName, parkingLot);
        return parkingManagerRepository.save(parkingManager);
    }

    public ParkingManager createParkingBoy(long managerId, ParkingBoy parkingBoy) {
        ParkingManager parkingManager = saveFindManagerById(managerId);
        parkingManager.hireParkingBoy(parkingBoy);
        return parkingManagerRepository.save(parkingManager);
    }

    public ParkingManager deleteParkingBoy(long manageId, String boyName) {
        ParkingManager parkingManager = saveFindManagerById(manageId);
        parkingManager.fireParkingBoy(boyName);
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

    private ParkingManager saveFindManagerById(long managerId) {
        return parkingManagerRepository.findById(managerId).orElseThrow(() -> {
            throw new ParkingManagerNotFoundException(404, "invalid id", "cannot find parking manager with id" + managerId);
        });
    }
}
