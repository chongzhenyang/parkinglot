package com.thoughtworks.chongzhen.parkinglot.api;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingBoy;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingLot;
import com.thoughtworks.chongzhen.parkinglot.service.ParkingBoyService;
import com.thoughtworks.chongzhen.parkinglot.service.ParkingManagerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parkingManagers")
@AllArgsConstructor
public class ParkingManagerApi {
    private final ParkingBoyService parkingBoyService;
    private final ParkingManagerService parkingManagerService;

    @PostMapping("/parkingBoys")
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingBoy createParkingBoy(@RequestBody ParkingBoy parkingBoy) {
        return parkingBoyService.createParkingBoy(parkingBoy);
    }

    @DeleteMapping("/parkingBoys/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ParkingBoy deleteParkingBoy(@PathVariable("id") long id){
        return parkingManagerService.deleteParkingBoy(id);
    }

    @PostMapping("/parkingBoys/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ParkingBoy createLot(@PathVariable("id") long id) {
        return parkingBoyService.createParkingLot(500, id);
    }

    @DeleteMapping("/parkingLots")
    public ParkingLot deleteLot(@RequestParam(value = "id", required = false) long id,
                                @RequestParam(value = "name", required = false) String name){
        return null;
    }

    @GetMapping("/parkingLots")
    @ResponseStatus(HttpStatus.OK)
    public List<ParkingLot> showParkingLot() {
        return parkingBoyService.getAllParkingLot();
    }

    @GetMapping("/parkingBoys")
    public List<ParkingBoy> showParkingBoy() {
        return parkingBoyService.getAllParkingBoy();
    }
}
