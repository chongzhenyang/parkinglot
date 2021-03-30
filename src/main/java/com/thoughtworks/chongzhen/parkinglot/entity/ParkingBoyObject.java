package com.thoughtworks.chongzhen.parkinglot.entity;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.Car;

public interface ParkingBoyObject {

    TicketObject park(Car car);

    Car pickUp(TicketObject ticketObject);
}
