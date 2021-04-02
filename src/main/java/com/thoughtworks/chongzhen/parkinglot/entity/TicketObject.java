package com.thoughtworks.chongzhen.parkinglot.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketObject {
    private long parkingBoyId;
    private long parkingLotId;
    private String ticketNumber;
    private boolean isParkedByManager;

    public static TicketObject createTicket(long parkingBoyId, long parkingLotId, String ticketNumber){
        return TicketObject.builder()
                .parkingBoyId(parkingBoyId)
                .parkingLotId(parkingLotId)
                .ticketNumber(ticketNumber)
                .isParkedByManager(false)
                .build();
    }
}
