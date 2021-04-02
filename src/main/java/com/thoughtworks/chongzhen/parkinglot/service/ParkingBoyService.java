package com.thoughtworks.chongzhen.parkinglot.service;

import com.thoughtworks.chongzhen.parkinglot.entity.ParkingBoyObject;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.Car;
import com.thoughtworks.chongzhen.parkinglot.entity.TicketObject;
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
    public TicketObject parkCar(Car car) {
        ParkingBoyObject parkingBoyObject = parkingBoyObjectRepository.findRandomParkingBoyObject();
        long[] longArgs = parkingBoyObject.park(car);
        parkingBoyObjectRepository.save(parkingBoyObject);

        String plateNumberEncoded = Base64.getEncoder()
                .encodeToString(car.getLicencePlate().getBytes(StandardCharsets.UTF_8));
        return TicketObject.createTicket(longArgs[0], longArgs[1], plateNumberEncoded);
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
