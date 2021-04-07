package com.thoughtworks.chongzhen.parkinglot.service;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.Car;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.ParkingLot;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.Staff;
import com.thoughtworks.chongzhen.parkinglot.entity.Ticket;
import com.thoughtworks.chongzhen.parkinglot.repository.CarRepository;
import com.thoughtworks.chongzhen.parkinglot.repository.ParkingLotRepository;
import com.thoughtworks.chongzhen.parkinglot.repository.StaffRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.UUID;


import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class StaffService implements UserDetailsService {

    private final StaffRepository staffRepository;

    private final ParkingLotRepository parkingLotRepository;

    private final CarRepository carRepository;

    public Ticket park(Car car, long lotId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
        if (authorities.stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_MANAGER"))) {
            return parkByManager(car, lotId);
        } else if (authorities.stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_SMART"))) {
            return parkBySmartBoy();
        } else if (authorities.stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_STUPID"))) {
            return parkByStupidBoy();
        }

        throw new RuntimeException("cannot park");
    }

    private Ticket parkByManager(Car car, long lotId) {
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        staffRepository.parkByManager(uuid, car.getBrand(), car.getLicencePlate(), car.getModel(), lotId);
        String plateNumber = Base64.getEncoder().encodeToString(car.getLicencePlate().getBytes(StandardCharsets.UTF_8));
        return Ticket.createTicket(lotId, plateNumber);
    }

    private Ticket parkBySmartBoy() {
        return null;
    }

    private Ticket parkByStupidBoy() {

        return null;
    }

    public Car pickUp(Ticket ticket) {
        return null;
    }

    public boolean hire(Staff staff) {
        return false;
    }

    public boolean fire(String staffName) {
        return false;
    }

    public ParkingLot createParkingLot(ParkingLot parkingLot) {
        return parkingLotRepository.save(parkingLot);
    }

    public ParkingLot deleteParkingLot(String parkingLotName) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Staff staff = staffRepository.findByUsername(s);
        if (!isNull(staff)) {
            return new User(s, staff.getPassword(),
                    staff.getAuthorities());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + s);
        }
    }
}
