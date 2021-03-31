package EntityBuilder;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.Car;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingLot;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class ParkingLotBuilder {
    private long id;

    private long lotsRemain;

    private String name;

    List<Car> cars;

    public static ParkingLotBuilder withDefault() {
        return new ParkingLotBuilder(1, 500, "first", Collections.emptyList());
    }

    public ParkingLotBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public ParkingLotBuilder withLotsRemain(long lotsRemain) {
        this.lotsRemain = lotsRemain;
        return this;
    }

    public ParkingLotBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ParkingLotBuilder withCar(List<Car> cars) {
        this.cars = cars;
        return this;
    }

    public ParkingLot build() {
        return ParkingLot.builder()
                .id(id)
                .lotsRemain(lotsRemain)
                .name(name)
                .cars(cars)
                .build();
    }
}
