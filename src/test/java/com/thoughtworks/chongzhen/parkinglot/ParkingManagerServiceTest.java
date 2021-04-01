package com.thoughtworks.chongzhen.parkinglot;

import EntityBuilder.*;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.Car;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingBoy;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingLot;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingManager;
import com.thoughtworks.chongzhen.parkinglot.entity.ParkingManagerObject;
import com.thoughtworks.chongzhen.parkinglot.entity.TicketObject;
import com.thoughtworks.chongzhen.parkinglot.repository.*;
import com.thoughtworks.chongzhen.parkinglot.service.ParkingManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.util.*;

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
    public void should_create_parking_manager_successful() {
        ParkingManager parkingManager = ParkingManagerBuilder.withDefault().build();

        when(parkingManagerRepository.save(parkingManager)).thenReturn(parkingManager);

        ParkingManager foundParkingManager = parkingManagerService.createParkingManager(parkingManager);

        assertThat(foundParkingManager).isEqualTo(parkingManager);
    }

    @Test
    public void should_create_parking_lot_successful(){
        ParkingManager parkingManager = ParkingManagerBuilder.withDefault().build();
        ParkingLot parkingLot = ParkingLot.builder().lotsRemain(500).name("secondLot").build();
        ParkingLot parkingLot1 = ParkingLotBuilder.withDefault().build();
        List<ParkingLot> parkingLotList = new ArrayList<>();
        parkingLotList.add(parkingLot1);
        parkingLotList.add(parkingLot);

        ParkingBoy parkingBoy = ParkingBoyBuilder.withDefault().build();
        parkingBoy.getParkingLots().add(parkingLot);
        List<ParkingBoy> parkingBoyList = new ArrayList<>();
        parkingBoyList.add(parkingBoy);
        ParkingManager expectedManager = ParkingManagerBuilder.withDefault().withParkingBoys(parkingBoyList).withParkingLots(parkingLotList).build();


        when(parkingManagerRepository.findById(1L)).thenReturn(Optional.of(parkingManager));
        when(parkingManagerRepository.save(parkingManager)).thenReturn(parkingManager);

        ParkingManager returnParkingManager = parkingManagerService.createParkingLot(500, 1, "zuowen", "secondLot");
        assertThat(returnParkingManager).isEqualTo(expectedManager);
    }

    @Test
    public void should_delete_parking_lot_successful(){
        ParkingManager parkingManager = ParkingManagerBuilder.withDefault().build();
        Optional<ParkingLot> parkingLot = Optional.of(ParkingLotBuilder.withDefault().build());
        ParkingBoy parkingBoy = ParkingBoyBuilder.withDefault().withParkingLots(new ArrayList<ParkingLot>()).build();
        List<ParkingBoy> parkingBoyList = new ArrayList<>();
        parkingBoyList.add(parkingBoy);
        ParkingManager expectedParkingManager = ParkingManagerBuilder.withDefault().withParkingBoys(parkingBoyList).withParkingLots(new ArrayList<ParkingLot>()).build();

        when(parkingManagerRepository.findById(1L)).thenReturn(Optional.of(parkingManager));
        when(parkingLotRepository.findParkingLotByName("firstLot")).thenReturn(parkingLot);
        when(parkingManagerRepository.save(expectedParkingManager)).thenReturn(expectedParkingManager);

        ParkingManager foundParkingManager = parkingManagerService.deleteParkingLot(1L, "zuowen", "firstLot");
        assertThat(foundParkingManager).isEqualTo(expectedParkingManager);
    }

    @Test
    public void should_create_parking_boy_successful(){
        ParkingManager parkingManager = ParkingManagerBuilder.withDefault().build();
        ParkingBoy parkingBoy = ParkingBoyBuilder.withDefault().withId(11).withName("shanyue").build();
        ParkingManager expectedParkingManager = ParkingManagerBuilder.withDefault().build();
        expectedParkingManager.getParkingBoys().add(parkingBoy);

        when(parkingManagerRepository.findById(1L)).thenReturn(Optional.of(parkingManager));
        when(parkingManagerRepository.save(expectedParkingManager)).thenReturn(expectedParkingManager);

        ParkingManager foundParkingManager = parkingManagerService.createParkingBoy(1L, parkingBoy);
        assertThat(foundParkingManager).isEqualTo(expectedParkingManager);
    }

    @Test
    public void should_not_delete_when_deleting_parking_boy_given_parking_boy_non_empty(){
        ParkingManager parkingManager = ParkingManagerBuilder.withDefault().build();

        when(parkingManagerRepository.findById(1L)).thenReturn(Optional.of(parkingManager));
        when(parkingManagerRepository.save(parkingManager)).thenReturn(parkingManager);

        ParkingManager foundParkingManager = parkingManagerService.deleteParkingBoy(1L, "zuowen");
        assertThat(foundParkingManager).isEqualTo(parkingManager);
    }

    @Test
    public void should_park_car_successful_given_parked_by_parking_manager(){
        Car car = CarBuilder.withDefault().withId(20).withBrand("BENZ").withModel("E63s").withLicencePlate("川AP9SA2").build();
        ParkingManager parkingManager = ParkingManagerBuilder.withDefault().build();
        ParkingManagerObject parkingManagerObject = ParkingManagerObject.builder()
                .id(parkingManager.getId())
                .name(parkingManager.getName())
                .parkingBoys(parkingManager.getParkingBoys())
                .parkingLots(parkingManager.getParkingLots())
                .build();
        String plateNumber = Base64.getEncoder().encodeToString(car.getLicencePlate().getBytes(StandardCharsets.UTF_8));
        TicketObject expectedTicket = TicketObjectBuilder.withDefault()
                .withBoyId(1L).withLotId(5).withTicketNumber(plateNumber).isParkedByManager(true).build();

        when(parkingManagerObjectRepository.findParkingManagerObjectById(1L)).thenReturn(parkingManagerObject);

        TicketObject foundTicket = parkingManagerService.park(car,1L);
        assertThat(foundTicket).isEqualTo(expectedTicket);
    }

    @Test
    public void should_pickup_car_successful_given_picked_up_by_manager(){
        Car car = CarBuilder.withDefault().build();
        ParkingManager parkingManager = ParkingManagerBuilder.withDefault().build();
        ParkingManagerObject parkingManagerObject = ParkingManagerObject.builder()
                .id(parkingManager.getId())
                .name(parkingManager.getName())
                .parkingBoys(parkingManager.getParkingBoys())
                .parkingLots(parkingManager.getParkingLots())
                .build();
        String plateNumber = Base64.getEncoder().encodeToString("川A5S11A".getBytes(StandardCharsets.UTF_8));
        TicketObject ticketObject = TicketObjectBuilder.withDefault()
                .withBoyId(1L).withLotId(5).withTicketNumber(plateNumber).isParkedByManager(true).build();
        when(parkingManagerObjectRepository.findParkingManagerObjectById(1L)).thenReturn(parkingManagerObject);

        Car foundCar = parkingManagerService.pickUp(ticketObject);
        assertThat(foundCar).isEqualTo(car);
    }
}
