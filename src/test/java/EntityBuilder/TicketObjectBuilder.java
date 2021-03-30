package EntityBuilder;

import com.thoughtworks.chongzhen.parkinglot.entity.TicketObject;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class TicketObjectBuilder {
    private long parkingBoyId;
    private long parkingLotId;
    private String ticketNumber;

    public static TicketObjectBuilder withDefault() {
        return new TicketObjectBuilder(1, 1, "fakeTicketNumber");
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

    public TicketObject build() {
        return TicketObject.builder()
                .parkingBoyId(parkingBoyId)
                .parkingLotId(parkingLotId)
                .ticketNumber(ticketNumber)
                .build();
    }
}
