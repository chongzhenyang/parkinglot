package EntityBuilder;

import com.thoughtworks.chongzhen.parkinglot.entity.TicketObject;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class TicketObjectBuilder {
    private long parkingBoyId;
    private long parkingLotId;
    private String ticketNumber;
    private boolean isParkedByManager;

    public static TicketObjectBuilder withDefault() {
        return new TicketObjectBuilder(1, 1, "fakeTicketNumber", true);
    }

    public TicketObjectBuilder withLotId(long parkingLotId) {
        this.parkingLotId = parkingLotId;
        return this;
    }

    public TicketObjectBuilder withBoyId(long parkingBoyId) {
        this.parkingBoyId = parkingBoyId;
        return this;
    }

    public TicketObjectBuilder withTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
        return this;
    }

    public TicketObjectBuilder isParkedByManager(boolean isParkedByManager) {
        this.isParkedByManager = isParkedByManager;
        return this;
    }

    public TicketObject build() {
        return TicketObject.builder()
                .parkingBoyId(parkingBoyId)
                .parkingLotId(parkingLotId)
                .ticketNumber(ticketNumber)
                .isParkedByManager(isParkedByManager)
                .build();
    }
}
