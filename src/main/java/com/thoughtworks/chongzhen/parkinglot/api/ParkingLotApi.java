package com.thoughtworks.chongzhen.parkinglot.api;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.Car;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingBoy;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingLot;
import com.thoughtworks.chongzhen.parkinglot.entity.TicketObject;
import com.thoughtworks.chongzhen.parkinglot.service.ApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parkingBoys")
@AllArgsConstructor
public class ParkingLotApi {
    private final ApplicationService applicationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingBoy createParkingBoy(@RequestBody ParkingBoy parkingBoy) {
        return applicationService.createParkingBoy(parkingBoy);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ParkingBoy createLot(@PathVariable("id") long id) {
        return applicationService.createParkingLot(500, id);
    }

    @GetMapping("/parkingLots")
    @ResponseStatus(HttpStatus.OK)
    public List<ParkingLot> showParkingLot() {
        return applicationService.getAllParkingLot();
    }

    @GetMapping()
    public List<ParkingBoy> showParkingBoy() {
        return applicationService.getAllParkingBoy();
    }

    @PostMapping("/park")
    @ResponseStatus(HttpStatus.OK)
    public TicketObject park(@RequestBody Car car) {
        return applicationService.parkCar(car);
    }

    @PostMapping("/pickUp")
    @ResponseStatus(HttpStatus.OK)
    public Car pickUp(@RequestBody TicketObject ticketObject) {
        return applicationService.pickUp(ticketObject);
    }
}
