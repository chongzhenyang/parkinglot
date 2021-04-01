package EntityBuilder;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingBoy;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingLot;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingManager;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ParkingManagerBuilder {
    private long id;

    private String name;

    List<ParkingBoy> parkingBoys;

    List<ParkingLot> parkingLots;

    public static ParkingManagerBuilder withDefault() {
        List<ParkingBoy> parkingBoyList = new ArrayList<>();
        ParkingBoy parkingBoy = ParkingBoyBuilder.withDefault().build();
        parkingBoyList.add(parkingBoy);

        List<ParkingLot> parkingLotList = new ArrayList<>();
        ParkingLot parkingLot = ParkingLotBuilder.withDefault().build();
        parkingLotList.add(parkingLot);
        return new ParkingManagerBuilder(1, "chongzhen", parkingBoyList, parkingLotList);
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

    public ParkingManagerBuilder withParkingLots(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
        return this;
    }

    public ParkingManager build() {
        return ParkingManager.builder()
                .id(id)
                .name(name)
                .parkingBoys(parkingBoys)
                .parkingLots(parkingLots)
                .build();
    }
}
