package com.thoughtworks.chongzhen.parkinglot.controller;

import com.thoughtworks.chongzhen.parkinglot.Jwt.JwtTokenUtil;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.Car;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingBoy;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingManager;
import com.thoughtworks.chongzhen.parkinglot.entity.Ticket;
import com.thoughtworks.chongzhen.parkinglot.exceptionHanding.exceptions.InvalidTicketException;
import com.thoughtworks.chongzhen.parkinglot.service.ParkingManagerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parkingManagers")
@AllArgsConstructor
public class ParkingManagerController {
    private final ParkingManagerService parkingManagerService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingManager createParkingManager(@RequestHeader("Authorization") String token) {
        String username = getUsernameFromToken(token);
        ParkingManager parkingManager = ParkingManager.builder().name(username).build();
        return parkingManagerService.createParkingManager(parkingManager);
    }

    @PostMapping("/{id}/parkingBoys")
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingManager createParkingBoy(@PathVariable("id") long managerId, @RequestBody ParkingBoy parkingBoy) {
        return parkingManagerService.createParkingBoy(managerId, parkingBoy);
    }

    @PostMapping("/parkingLots")
    public ParkingManager createParkingLot(@RequestParam("capacity") long capacity,
                                           @RequestParam("managerId") long id,
                                           @RequestParam("parkingBoyName") String boyName,
                                           @RequestParam("lotName") String lotName) {
        return parkingManagerService.createParkingLot(capacity, id, boyName, lotName);
    }

    @DeleteMapping("/parkingBoys")
    @ResponseStatus(HttpStatus.OK)
    public ParkingManager deleteParkingBoy(@RequestParam("managerId") long id,
                                           @RequestParam("boyName") String boyName) {
        return parkingManagerService.deleteParkingBoy(id, boyName);
    }

    @DeleteMapping("/parkingLots")
    public ParkingManager deleteLot(@RequestParam("managerId") long managerId,
                                    @RequestParam("boyName") String boyName,
                                    @RequestParam("lotName") String lotName) {
        return parkingManagerService.deleteParkingLot(managerId, boyName, lotName);
    }

    @PostMapping("/{id}/park")
    @ResponseStatus(HttpStatus.OK)
    public Ticket park(@PathVariable("id") long id, @RequestBody Car car) {
        return parkingManagerService.park(car, id);
    }

    @PostMapping("/pickUp")
    @ResponseStatus(HttpStatus.OK)
    public Car pickUp(@RequestBody Ticket ticket) {
        if (!ticket.isParkedByManager()) {
            throw new InvalidTicketException(400, "invalid ticket", "car was parked by parking boy");
        }
        return parkingManagerService.pickUp(ticket);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ParkingManager> show() {
        return parkingManagerService.getAll();
    }

    private String getUsernameFromToken(String token){
        return jwtTokenUtil.getUsernameFromToken(token.substring(7));
    }
}
