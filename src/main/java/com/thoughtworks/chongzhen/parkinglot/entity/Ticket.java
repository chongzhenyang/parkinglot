package com.thoughtworks.chongzhen.parkinglot.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Ticket {
    private long parkingLotId;
    private String ticketNumber;

    public static Ticket createTicket(long parkingLotId, String ticketNumber){
        return Ticket.builder()
                .parkingLotId(parkingLotId)
                .ticketNumber(ticketNumber)
                .build();
    }
}
