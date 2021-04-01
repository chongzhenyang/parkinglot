package EntityBuilder;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingBoy;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingLot;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class ParkingBoyBuilder {
    private long id;
    private String name;
    private boolean isSmart;
    private long previousVisitedLot;
    private List<ParkingLot> parkingLots;

    public static ParkingBoyBuilder withDefault() {
        List<ParkingLot> parkingLotList = new ArrayList<>();
        ParkingLot parkingLot = ParkingLotBuilder.withDefault().build();
        parkingLotList.add(parkingLot);
        return new ParkingBoyBuilder(4, "zuowen", false, 5, parkingLotList);
    }

    public ParkingBoyBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public ParkingBoyBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ParkingBoyBuilder withIsSmart(boolean isSmart) {
        this.isSmart = isSmart;
        return this;
    }

    public ParkingBoyBuilder withPreviousVisitedLot(long lotNum) {
        this.previousVisitedLot = lotNum;
        return this;
    }

    public ParkingBoyBuilder withParkingLots(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
        return this;
    }

    public ParkingBoy build() {
        return ParkingBoy.builder()
                .id(id)
                .name(name)
                .isSmart(isSmart)
                .previousVisitedLot(previousVisitedLot)
                .parkingLots(parkingLots)
                .build();
    }
}
