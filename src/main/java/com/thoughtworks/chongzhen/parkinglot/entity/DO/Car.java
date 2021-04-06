package com.thoughtworks.chongzhen.parkinglot.entity.DO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String brand;

    private String model;

    @Column(name = "licencePlate", nullable = false, unique = true)
    private String licencePlate;
}
