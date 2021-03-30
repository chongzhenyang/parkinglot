package com.thoughtworks.chongzhen.parkinglot.entity.DO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long lotsRemain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parkingBoy_id")
    private ParkingBoy parkingBoy;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    List<Car> cars = new ArrayList<>();
}
