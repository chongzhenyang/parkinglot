package com.thoughtworks.chongzhen.parkinglot;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.Car;
import com.thoughtworks.chongzhen.parkinglot.entity.TicketObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ParkingLotApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParkingBoyApiTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void should_return_all_parking_boy(){
        ResponseEntity<String> responseEntity = restTemplate
                .getForEntity("http://localhost:" + port + "/parkingBoys", String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List parkingBoys = restTemplate
                .getForObject("http://localhost:" + port + "/parkingBoys", List.class);
        assertEquals(0, parkingBoys.size());
    }

    void could_park_car_and_get_ticket_successfully() {
        // call api of "/parking"
        // assert ticket
    }

    @Test
    public void should_park_car_into_parking_lot_then_pick_up_from_parking_lot(){
        Car car = Car.builder().brand("BENZ")
                .model("E63s AMG")
                .licencePlate("川AA765B")
                .build();
        String plateEncoded = Base64.getEncoder().encodeToString(car.getLicencePlate().getBytes(StandardCharsets.UTF_8));

        ResponseEntity<TicketObject> parkResponse = restTemplate
                .postForEntity("http://localhost:" + port + "/parkingBoys/park", car, TicketObject.class);
        assertEquals(HttpStatus.OK, parkResponse.getStatusCode());
        assertEquals(plateEncoded, parkResponse.getBody().getTicketNumber());


        TicketObject ticketObject = parkResponse.getBody();
        ResponseEntity<Car> pickUpResponse = restTemplate
                .postForEntity("http://localhost:" + port + "/parkingBoys/pickUp", ticketObject, Car.class);
        Car foundCar = pickUpResponse.getBody();
        assertEquals(HttpStatus.OK, pickUpResponse.getStatusCode());
        assertEquals("BENZ", foundCar.getBrand());
        assertEquals("E63s AMG", foundCar.getModel());
        assertEquals("川AA765B", foundCar.getLicencePlate());
    }


}
