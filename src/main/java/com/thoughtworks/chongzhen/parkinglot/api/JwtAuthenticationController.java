package com.thoughtworks.chongzhen.parkinglot.api;

import com.thoughtworks.chongzhen.parkinglot.entity.DTO.JwtRequest;
import com.thoughtworks.chongzhen.parkinglot.entity.DTO.JwtResponse;
import com.thoughtworks.chongzhen.parkinglot.Jwt.JwtTokenUtil;
import com.thoughtworks.chongzhen.parkinglot.Jwt.JwtUserDetailsService;
import com.thoughtworks.chongzhen.parkinglot.exceptionHanding.exceptions.InvalidUsernameAndPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/authenticate")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    /**
     * 获取 客户端来的 username password 使用秘钥加密成 json web token
     * */
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
