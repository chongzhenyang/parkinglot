package com.thoughtworks.chongzhen.parkinglot.exceptionHanding;

import com.thoughtworks.chongzhen.parkinglot.exceptionHanding.exceptions.NoParkingLotException;
import com.thoughtworks.chongzhen.parkinglot.exceptionHanding.exceptions.ParkingBoyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoParkingLotException.class)
    public ResponseEntity<ErrorResult> handle(NoParkingLotException ex) {
        ErrorResult errorResult = new ErrorResult(ex.getErrorCode(), ex.getError(), ex.getErrorMsg());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResult);
    }

    @ExceptionHandler(ParkingBoyNotFoundException.class)
    public ResponseEntity<ErrorResult> handle(ParkingBoyNotFoundException ex) {
        ErrorResult errorResult = new ErrorResult(ex.getErrorCode(), ex.getError(), ex.getErrorMsg());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResult);
    }
}
