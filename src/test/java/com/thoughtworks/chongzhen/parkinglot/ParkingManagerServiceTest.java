package com.thoughtworks.chongzhen.parkinglot;

import EntityBuilder.CarBuilder;
import EntityBuilder.ParkingBoyBuilder;
import EntityBuilder.TicketObjectBuilder;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.Car;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingBoy;
import com.thoughtworks.chongzhen.parkinglot.entity.ParkingBoyFactory;
import com.thoughtworks.chongzhen.parkinglot.entity.ParkingBoyObject;
import com.thoughtworks.chongzhen.parkinglot.entity.TicketObject;
import com.thoughtworks.chongzhen.parkinglot.repository.ParkingBoyObjectRepository;
import com.thoughtworks.chongzhen.parkinglot.repository.ParkingLotRepository;
import com.thoughtworks.chongzhen.parkinglot.service.ParkingManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ParkingManagerServiceTest {
    private ParkingManagerService parkingManagerService;

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @Mock
    private ParkingBoyObjectRepository parkingBoyObjectRepository;

    @BeforeEach
    void setUp(){
        parkingManagerService = new ParkingManagerService(parkingLotRepository, parkingBoyObjectRepository);
    }

    @Test
    public void should_delete_parking_boy_successful(){
        //given
        TicketObject ticketObject = TicketObjectBuilder.withDefault().build();
        ParkingBoy parkingBoy = ParkingBoyBuilder.withDefault().build();
        ParkingBoyObject parkingBoyObject = new ParkingBoyFactory().getParkingBoy(parkingBoy);
        //when
        when(parkingBoyObjectRepository.findParkingBoyObjectByTicket(ticketObject)).thenReturn(parkingBoyObject);
        //then
        ParkingBoy foundParkingBoy = parkingManagerService.deleteParkingBoy(1);
        assertThat(parkingBoy).isEqualTo(foundParkingBoy);
    }
}
