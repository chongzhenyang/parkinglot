package com.thoughtworks.chongzhen.parkinglot.exceptionHanding.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.security.InvalidParameterException;

@AllArgsConstructor
public class InvalidTicketException extends InvalidParameterException {
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
