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

    private String name;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "parkingLot_id")
    List<Car> cars = new ArrayList<>();
}
