package com.thoughtworks.chongzhen.parkinglot.repository;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findByUsername(String username);
}
