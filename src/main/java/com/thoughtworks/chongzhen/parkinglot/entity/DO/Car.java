package com.thoughtworks.chongzhen.parkinglot.entity.DO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@GenericGenerator(name = "system-uuid", strategy = "uuid")
public class Car {
    @Id
    @GeneratedValue(generator = "system-uuid")
    private String id;

    private String brand;

    private String model;

    @Column(name = "licencePlate", nullable = false, unique = true)
    private String licencePlate;
}
