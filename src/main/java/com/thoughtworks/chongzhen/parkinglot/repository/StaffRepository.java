package com.thoughtworks.chongzhen.parkinglot.repository;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    Staff findByUsername(String username);

    @Transactional
    @Modifying
    @Query(nativeQuery=true, value="INSERT INTO car (id, brand, licence_plate, model, parking_lot_id) VALUES (?1, ?2, ?3, ?4, ?5)")
    int parkByManager(String id, String brand, String licencePlate, String model, long lotId);
}
