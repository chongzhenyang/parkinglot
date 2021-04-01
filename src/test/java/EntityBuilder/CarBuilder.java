package EntityBuilder;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.Car;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingLot;
import com.thoughtworks.chongzhen.parkinglot.entity.TicketObject;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CarBuilder {
    private long id;
    private String brand;
    private String model;
    private String licencePlate;

    public static CarBuilder withDefault() {
        return new CarBuilder(1, "BMW", "540i", "川A5S11A");
    }

    public CarBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public CarBuilder withBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public CarBuilder withModel(String Model) {
        this.model = model;
        return this;
    }

    public CarBuilder withLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
        return this;
    }

    public Car build() {
        return Car.builder()
                .id(id)
                .brand(brand)
                .model(model)
                .licencePlate(licencePlate)
                .build();
    }
}
