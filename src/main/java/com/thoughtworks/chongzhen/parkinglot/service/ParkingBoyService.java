package com.thoughtworks.chongzhen.parkinglot.service;

import com.thoughtworks.chongzhen.parkinglot.entity.ParkingBoyObject;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.Car;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingBoy;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingLot;
import com.thoughtworks.chongzhen.parkinglot.entity.TicketObject;
import com.thoughtworks.chongzhen.parkinglot.exceptionHanding.exceptions.ParkingBoyNotFoundException;
import com.thoughtworks.chongzhen.parkinglot.repository.CarRepository;
import com.thoughtworks.chongzhen.parkinglot.repository.ParkingBoyObjectRepository;
import com.thoughtworks.chongzhen.parkinglot.repository.ParkingBoyRepository;
import com.thoughtworks.chongzhen.parkinglot.repository.ParkingLotRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class ParkingBoyService {

    private final ParkingLotRepository parkingLotRepository;

    private final CarRepository carRepository;

    private final ParkingBoyRepository parkingBoyRepository;

    private final ParkingBoyObjectRepository parkingBoyObjectRepository;

    public ParkingBoy createParkingBoy(ParkingBoy parkingBoy) {
        return parkingBoyRepository.save(parkingBoy);
    }

    public List<ParkingLot> getAllParkingLot() {
        return parkingLotRepository.findAll();
    }

    public List<ParkingBoy> getAllParkingBoy() {
        return parkingBoyRepository.findAll();
    }

    public ParkingBoy createParkingLot(long lotCapacity, long id) {
        ParkingLot parkingLot = ParkingLot.builder().lotsRemain(lotCapacity).build();
        ParkingBoy parkingBoy = parkingBoyRepository.findById(id).orElseThrow(() -> {
            throw new ParkingBoyNotFoundException(404, "invalid id", "cannot find parking boy with id" + id);
        });
        parkingBoy.getParkingLots().add(parkingLot);
        return parkingBoyRepository.save(parkingBoy);
    }

    @Transactional
    public TicketObject parkCar(Car car) {
        ParkingBoyObject parkingBoyObject = parkingBoyObjectRepository.findRandomParkingBoyObject();
        TicketObject ticketObject = parkingBoyObject.park(car);
//        ticket = Ticket.create(parkingBoyObject.id, car.getId(),...)
//        ticketRepo.save(ticket)
//        return ticket;
        parkingBoyObjectRepository.save(parkingBoyObject);

        return ticketObject;
    }

    @Transactional
    public Car pickUp(TicketObject ticketObject) {
        ParkingBoyObject parkingBoyObject = parkingBoyObjectRepository.findParkingBoyObjectByTicket(ticketObject);
        Car car = parkingBoyObject.pickUp(ticketObject);
        parkingBoyObjectRepository.save(parkingBoyObject);
        carRepository.delete(car);
        return car;
    }
}
