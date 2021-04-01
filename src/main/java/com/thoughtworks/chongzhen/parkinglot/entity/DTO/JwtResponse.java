package com.thoughtworks.chongzhen.parkinglot.entity.DTO;


import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String Jwttoken;

    public String getToken() {
        return this.Jwttoken;
    }
}
