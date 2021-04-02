package com.thoughtworks.chongzhen.parkinglot;

import EntityBuilder.CarBuilder;
import EntityBuilder.ParkingBoyBuilder;
import EntityBuilder.TicketObjectBuilder;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.Car;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingBoy;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingLot;
import com.thoughtworks.chongzhen.parkinglot.entity.ParkingBoyFactory;
import com.thoughtworks.chongzhen.parkinglot.entity.AbstractParkingBoy;
import com.thoughtworks.chongzhen.parkinglot.entity.Ticket;
import com.thoughtworks.chongzhen.parkinglot.exceptionHanding.exceptions.CarNotFoundException;
import com.thoughtworks.chongzhen.parkinglot.exceptionHanding.exceptions.NoParkingLotException;
import com.thoughtworks.chongzhen.parkinglot.repository.CarRepository;
import com.thoughtworks.chongzhen.parkinglot.repository.ParkingBoyObjectRepository;
import com.thoughtworks.chongzhen.parkinglot.service.ParkingBoyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ParkingBoyServiceTest {
    private ParkingBoyService parkingBoyService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private ParkingBoyObjectRepository parkingBoyObjectRepository;

    @BeforeEach
    void setUp() {
        parkingBoyService = new ParkingBoyService(carRepository, parkingBoyObjectRepository);
    }

    @Test
    public void should_park_car_successful(){
        ParkingBoy parkingBoy = ParkingBoyBuilder.withDefault().withPreviousVisitedLot(1).build();
        Car car = CarBuilder.withDefault().build();
        String plate = Base64.getEncoder().encodeToString(car.getLicencePlate().getBytes(StandardCharsets.UTF_8));
        AbstractParkingBoy abstractParkingBoy = ParkingBoyFactory.getParkingBoy(parkingBoy);
        Ticket expectTicket = TicketObjectBuilder.withDefault()
                .withBoyId(4)
                .withLotId(parkingBoy.getParkingLots().get(0).getId())
                .withTicketNumber(plate)
                .isParkedByManager(false)
                .build();

        when(parkingBoyObjectRepository.findRandomParkingBoyObject()).thenReturn(abstractParkingBoy);
        when(parkingBoyObjectRepository.save(abstractParkingBoy)).thenReturn(parkingBoy);

        Ticket foundTicket = parkingBoyService.parkCar(car);
        assertThat(foundTicket).isEqualTo(expectTicket);
    }

    @Test
    public void should_throw_exception_when_parking_given_all_parking_lots_full(){
        ParkingBoy parkingBoy = ParkingBoyBuilder.withDefault().withPreviousVisitedLot(1).build();
        parkingBoy.getParkingLots().get(0).setLotsRemain(0);
        Car car = CarBuilder.withDefault().build();
        AbstractParkingBoy abstractParkingBoy = ParkingBoyFactory.getParkingBoy(parkingBoy);

        when(parkingBoyObjectRepository.findRandomParkingBoyObject()).thenReturn(abstractParkingBoy);
        assertThrows(NoParkingLotException.class, ()->parkingBoyService.parkCar(car));
    }

    @Test
    public void should_throw_exception_when_parking_given_no_parking_lot_found(){
        ParkingBoy parkingBoy = ParkingBoyBuilder.withDefault().withPreviousVisitedLot(1).build();
        Car car = CarBuilder.withDefault().build();
        parkingBoy.setParkingLots(new ArrayList<ParkingLot>());
        AbstractParkingBoy abstractParkingBoy = ParkingBoyFactory.getParkingBoy(parkingBoy);

        when(parkingBoyObjectRepository.findRandomParkingBoyObject()).thenReturn(abstractParkingBoy);
        assertThrows(NoParkingLotException.class, ()->parkingBoyService.parkCar(car));
    }

    @Test
    public void should_pick_up_car_successful(){
        Car car = CarBuilder.withDefault().build();
        String plate = Base64.getEncoder().encodeToString(car.getLicencePlate().getBytes(StandardCharsets.UTF_8));
        Ticket ticket = TicketObjectBuilder.withDefault().withBoyId(4).withLotId(5).isParkedByManager(false).withTicketNumber(plate).build();
        ParkingBoy parkingBoy = ParkingBoyBuilder.withDefault().withPreviousVisitedLot(1).build();
        AbstractParkingBoy abstractParkingBoy = ParkingBoyFactory.getParkingBoy(parkingBoy);

        when(parkingBoyObjectRepository.findParkingBoyObjectByTicket(ticket)).thenReturn(abstractParkingBoy);
        when(parkingBoyObjectRepository.save(abstractParkingBoy)).thenReturn(parkingBoy);

        Car foundCar = parkingBoyService.pickUp(ticket);
        assertThat(foundCar).isEqualTo(car);
    }

    @Test
    public void should_throw_exception_when_pick_up_given_invalid_ticket_lot_number(){
        Car car = CarBuilder.withDefault().build();
        String plate = Base64.getEncoder().encodeToString(car.getLicencePlate().getBytes(StandardCharsets.UTF_8));
        Ticket ticket = TicketObjectBuilder.withDefault().withBoyId(4).withLotId(6).isParkedByManager(false).withTicketNumber(plate).build();
        ParkingBoy parkingBoy = ParkingBoyBuilder.withDefault().withPreviousVisitedLot(1).build();
        AbstractParkingBoy abstractParkingBoy = ParkingBoyFactory.getParkingBoy(parkingBoy);

        when(parkingBoyObjectRepository.findParkingBoyObjectByTicket(ticket)).thenReturn(abstractParkingBoy);

        assertThrows(NoParkingLotException.class, ()->parkingBoyService.pickUp(ticket));
    }

    @Test
    public void should_throw_exception_when_pick_up_given_invalid_ticket_number(){
        Car car = CarBuilder.withDefault().build();
        String plate = Base64.getEncoder().encodeToString(car.getLicencePlate().getBytes(StandardCharsets.UTF_8));
        Ticket ticket = TicketObjectBuilder.withDefault().withBoyId(4).withLotId(5).isParkedByManager(false).withTicketNumber("123asd").build();
        ParkingBoy parkingBoy = ParkingBoyBuilder.withDefault().withPreviousVisitedLot(1).build();
        AbstractParkingBoy abstractParkingBoy = ParkingBoyFactory.getParkingBoy(parkingBoy);

        when(parkingBoyObjectRepository.findParkingBoyObjectByTicket(ticket)).thenReturn(abstractParkingBoy);

        assertThrows(CarNotFoundException.class, ()->parkingBoyService.pickUp(ticket));
    }
}
