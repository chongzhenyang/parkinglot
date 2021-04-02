package com.thoughtworks.chongzhen.parkinglot.service;

import com.thoughtworks.chongzhen.parkinglot.entity.AbstractParkingBoy;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.Car;
import com.thoughtworks.chongzhen.parkinglot.entity.Ticket;
import com.thoughtworks.chongzhen.parkinglot.repository.CarRepository;
import com.thoughtworks.chongzhen.parkinglot.repository.ParkingBoyObjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@AllArgsConstructor
public class ParkingBoyService {

    private final CarRepository carRepository;

    private final ParkingBoyObjectRepository parkingBoyObjectRepository;

    @Transactional
    public Ticket parkCar(Car car) {
        AbstractParkingBoy abstractParkingBoy = parkingBoyObjectRepository.findRandomParkingBoyObject();
        long[] longArgs = abstractParkingBoy.park(car);
        parkingBoyObjectRepository.save(abstractParkingBoy);

        String plateNumberEncoded = Base64.getEncoder()
                .encodeToString(car.getLicencePlate().getBytes(StandardCharsets.UTF_8));
        return Ticket.createTicket(longArgs[0], longArgs[1], plateNumberEncoded);
    }

    @Transactional
    public Car pickUp(Ticket ticket) {
        AbstractParkingBoy abstractParkingBoy = parkingBoyObjectRepository.findParkingBoyObjectByTicket(ticket);
        Car car = abstractParkingBoy.pickUp(ticket);
        parkingBoyObjectRepository.save(abstractParkingBoy);
        carRepository.delete(car);
        return car;
    }
}
