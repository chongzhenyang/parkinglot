package com.thoughtworks.chongzhen.parkinglot;

import EntityBuilder.*;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.Car;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingBoy;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingLot;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingManager;
import com.thoughtworks.chongzhen.parkinglot.entity.ParkingBoyFactory;
import com.thoughtworks.chongzhen.parkinglot.entity.ParkingBoyObject;
import com.thoughtworks.chongzhen.parkinglot.entity.TicketObject;
import com.thoughtworks.chongzhen.parkinglot.exceptionHanding.exceptions.ParkingManagerNotFoundException;
import com.thoughtworks.chongzhen.parkinglot.repository.*;
import com.thoughtworks.chongzhen.parkinglot.service.ParkingManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ParkingManagerServiceTest {
    private ParkingManagerService parkingManagerService;

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @Mock
    private ParkingBoyObjectRepository parkingBoyObjectRepository;

    @Mock
    private ParkingManagerRepository parkingManagerRepository;

    @Mock
    private ParkingManagerObjectRepository parkingManagerObjectRepository;

    @BeforeEach
    void setUp() {
        parkingManagerService = new ParkingManagerService(parkingLotRepository, parkingManagerRepository, parkingManagerObjectRepository);
    }

    @Test
    public void should_create_parking_lot_successful(){
        ParkingBoy parkingBoy = ParkingBoyBuilder.withDefault().build();
        ParkingManager parkingManager = ParkingManagerBuilder.withDefault().withParkingBoys(Arrays.asList(parkingBoy)).build();

        when(parkingManagerRepository.findById(1L)).thenReturn(Optional.of(parkingManager));
        when(parkingManagerRepository.save(parkingManager)).thenReturn(parkingManager);

        ParkingLot parkingLot = ParkingLotBuilder.withDefault().build();
        ParkingBoy expectedParkingBoy = ParkingBoyBuilder.withDefault().withParkingLots(Arrays.asList(parkingLot)).build();
        ParkingManager expectedManager = ParkingManagerBuilder.withDefault().withParkingBoys(Arrays.asList(expectedParkingBoy)).build();
        ParkingManager returnParkingManager = parkingManagerService.createParkingLot(500, 1, "zuowen", "firstLot");
        assertThat(returnParkingManager).isEqualTo(expectedManager);
    }

    @Test
    public void should_throw_exception_when_create_parking_lot_given_invalid_manager_id(){

    }

    @Test
    public void should_throw_exception_when_create_parking_lot_given_invalid_parking_boy_name(){

    }


    //TODO: changed
    @Test
    public void should_delete_parking_boy_successful() {
        //given
        TicketObject ticketObject = TicketObjectBuilder.withDefault().build();
        ParkingBoy parkingBoy = ParkingBoyBuilder.withDefault().build();
        ParkingBoyObject parkingBoyObject = new ParkingBoyFactory().getParkingBoy(parkingBoy);
        //when
        when(parkingBoyObjectRepository.findParkingBoyObjectByTicket(ticketObject)).thenReturn(parkingBoyObject);
        //then
        ParkingManager parkingManager = parkingManagerService.deleteParkingBoy(1, "zuowen");
        assertThat(parkingBoy).isEqualTo(parkingManager);
    }
}
