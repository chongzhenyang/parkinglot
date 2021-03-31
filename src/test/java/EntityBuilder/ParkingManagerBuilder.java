package EntityBuilder;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingBoy;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingManager;
import com.thoughtworks.chongzhen.parkinglot.entity.TicketObject;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ParkingManagerBuilder {
    private long id;

    private String name;

    List<ParkingBoy> parkingBoys;

    public static ParkingManagerBuilder withDefault() {
        return new ParkingManagerBuilder(1, "chongzhen", new ArrayList<ParkingBoy>());
    }

    public ParkingManagerBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public ParkingManagerBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ParkingManagerBuilder withParkingBoys(List<ParkingBoy> parkingBoys) {
        this.parkingBoys = parkingBoys;
        return this;
    }

    public ParkingManager build() {
        return ParkingManager.builder()
                .id(id)
                .name(name)
                .parkingBoys(parkingBoys)
                .build();
    }
}
