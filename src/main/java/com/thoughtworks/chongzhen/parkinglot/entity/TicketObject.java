package com.thoughtworks.chongzhen.parkinglot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketObject {
    private long parkingBoyId;
    private long parkingLotId;
    private String ticketNumber;
    private boolean isParkedByManager;
}
