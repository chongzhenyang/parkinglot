package com.thoughtworks.chongzhen.parkinglot.controller;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.Car;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingLot;
import com.thoughtworks.chongzhen.parkinglot.entity.Ticket;
import com.thoughtworks.chongzhen.parkinglot.service.StaffService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parkingLots")
@AllArgsConstructor
public class ParkingApplicationController {

    private final StaffService staffService;

    @GetMapping("/test")
    public String test() {
        return "hello";
    }


    @PostMapping("/{lotId}/park")
    public Ticket park(@PathVariable("lotId") long id, @RequestBody Car car) {
        return staffService.park(car, id);
    }

    @PostMapping("/pickUp")
    public Car pickUp(@RequestBody Ticket ticket) {
        return null;
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @PostMapping()
    public ParkingLot createParkingLot(@RequestBody ParkingLot parkingLot) {
        return staffService.createParkingLot(parkingLot);
    }
}
