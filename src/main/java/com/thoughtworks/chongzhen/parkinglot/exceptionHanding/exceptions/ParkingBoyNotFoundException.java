package com.thoughtworks.chongzhen.parkinglot.exceptionHanding.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.NoSuchElementException;

@AllArgsConstructor
public class ParkingBoyNotFoundException extends NoSuchElementException {
    @Setter
    @Getter
    private int errorCode;

    @Setter
    @Getter
    private String error;

    @Setter
    @Getter
    private String errorMsg;
}
