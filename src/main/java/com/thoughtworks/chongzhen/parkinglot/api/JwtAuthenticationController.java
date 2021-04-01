package com.thoughtworks.chongzhen.parkinglot.api;

import com.thoughtworks.chongzhen.parkinglot.entity.DTO.JwtRequest;
import com.thoughtworks.chongzhen.parkinglot.entity.DTO.JwtResponse;
import com.thoughtworks.chongzhen.parkinglot.Jwt.JwtTokenUtil;
import com.thoughtworks.chongzhen.parkinglot.Jwt.JwtUserDetailsService;
import com.thoughtworks.chongzhen.parkinglot.exceptionHanding.exceptions.InvalidUsernameAndPasswordException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/authenticate")
@AllArgsConstructor
public class JwtAuthenticationController {

    private final JwtTokenUtil jwtTokenUtil;

    private final JwtUserDetailsService userDetailsService;

    @PostMapping
    public JwtResponse createAuthenticationToken(@RequestBody JwtRequest authenticationRequest){

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        if(!userDetails.getPassword().equals(authenticationRequest.getPassword())){
            throw new InvalidUsernameAndPasswordException(401, "unauthorized", "incorrect username and password");
        }

        final String token = jwtTokenUtil.generateToken(userDetails);

        return new JwtResponse(token);
    }
}
