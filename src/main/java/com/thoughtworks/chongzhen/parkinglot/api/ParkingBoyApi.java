package com.thoughtworks.chongzhen.parkinglot.api;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.Car;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingBoy;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingLot;
import com.thoughtworks.chongzhen.parkinglot.entity.TicketObject;
import com.thoughtworks.chongzhen.parkinglot.service.ParkingBoyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parkingBoys")
@AllArgsConstructor
public class ParkingBoyApi {
    private final ParkingBoyService parkingBoyService;

    @PostMapping("/park")
    @ResponseStatus(HttpStatus.OK)
    public TicketObject park(@RequestBody Car car) {
        return parkingBoyService.parkCar(car);
    }

    @PostMapping("/pickUp")
    @ResponseStatus(HttpStatus.OK)
    public Car pickUp(@RequestBody TicketObject ticketObject) {
        return parkingBoyService.pickUp(ticketObject);
    }
}
