package com.thoughtworks.chongzhen.parkinglot.repository;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
