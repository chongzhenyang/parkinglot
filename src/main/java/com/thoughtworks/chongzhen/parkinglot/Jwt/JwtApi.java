package com.thoughtworks.chongzhen.parkinglot.Jwt;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jwt")
@AllArgsConstructor
public class JwtApi {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String test(){
        return "secrete massage";
    }
}
