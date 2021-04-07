package com.thoughtworks.chongzhen.parkinglot.controller;

import com.thoughtworks.chongzhen.parkinglot.entity.DO.Role;
import com.thoughtworks.chongzhen.parkinglot.entity.DO.Staff;
import com.thoughtworks.chongzhen.parkinglot.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public String createToken(@RequestParam("username") String username, @RequestParam("password") String password) throws AuthenticationException {
        return authenticationService.login(username, password);
    }

    @PostMapping("/register")
    public void register(@RequestParam("username") String username, @RequestParam("password") String password) throws AuthenticationException {
        authenticationService.register(username, password);
    }

    @PostMapping("/add")
    public Staff addRole(@RequestParam("username") String username, @RequestParam("roleId") long roleId){
        return authenticationService.addRole(username, roleId);
    }

    @PostMapping("/role")
    public Role addRole(@RequestParam("role") String roleName){
        return authenticationService.createRole(roleName);
    }
}
