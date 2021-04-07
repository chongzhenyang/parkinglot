package com.thoughtworks.chongzhen.parkinglot.service;

import com.thoughtworks.chongzhen.parkinglot.Jwt.JwtTokenUtil;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.Role;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.Staff;
import com.thoughtworks.chongzhen.parkinglot.repository.RoleRepository;
import com.thoughtworks.chongzhen.parkinglot.repository.StaffRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;

    private final StaffService userDetailsService;

    private final StaffRepository staffRepository;

    private final RoleRepository roleRepository;

    private final JwtTokenUtil jwtTokenUtil;


    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }

    public void register(String username, String password) {
        if (staffRepository.findByUsername(username) != null) {
            throw new RuntimeException("duplicated username");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = encoder.encode(password);
        Staff staff = Staff.builder().username(username).password(rawPassword).build();
        staffRepository.save(staff);
    }

    public Staff addRole(String username, long roleId){
        Staff staff = staffRepository.findByUsername(username);
        Role role = roleRepository.findById(roleId).orElseThrow(IllegalArgumentException::new);
        staff.getRoles().add(role);
        return staffRepository.save(staff);
    }

    public Role createRole(String roleName){
        Role role = Role.builder().name(roleName).build();
        return roleRepository.save(role);
    }
}
