package com.thoughtworks.chongzhen.parkinglot.service;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingBoy;
import com.thoughtworks.chongzhen.parkinglot.repository.ParkingBoyObjectRepository;
import com.thoughtworks.chongzhen.parkinglot.repository.ParkingLotRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ParkingManagerService {
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingBoyObjectRepository parkingBoyObjectRepository;

    public ParkingBoy deleteParkingBoy(long id){
        return null;
    }
}
