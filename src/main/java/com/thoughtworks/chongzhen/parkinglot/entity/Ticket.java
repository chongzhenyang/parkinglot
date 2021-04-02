package com.thoughtworks.chongzhen.parkinglot.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Ticket {
    private long parkingBoyId;
    private long parkingLotId;
    private String ticketNumber;
    private boolean isParkedByManager;

    public static Ticket createTicket(long parkingBoyId, long parkingLotId, String ticketNumber){
        return Ticket.builder()
                .parkingBoyId(parkingBoyId)
                .parkingLotId(parkingLotId)
                .ticketNumber(ticketNumber)
                .isParkedByManager(false)
                .build();
    }
}
