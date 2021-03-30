package com.thoughtworks.chongzhen.parkinglot.exceptionHanding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResult {
    private int status;
    private String error;
    private String message;
}
