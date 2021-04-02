package com.thoughtworks.chongzhen.parkinglot.controller;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.Car;
import com.thoughtworks.chongzhen.parkinglot.entity.Ticket;
import com.thoughtworks.chongzhen.parkinglot.service.ParkingBoyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parkingBoys")
@AllArgsConstructor
public class ParkingBoyController {
    private final ParkingBoyService parkingBoyService;

    @PostMapping("/park")
    @ResponseStatus(HttpStatus.OK)
    public Ticket park(@RequestBody Car car) {
        return parkingBoyService.parkCar(car);
    }

    @PostMapping("/pickUp")
    @ResponseStatus(HttpStatus.OK)
    public Car pickUp(@RequestBody Ticket ticket) {
        return parkingBoyService.pickUp(ticket);
    }
}
