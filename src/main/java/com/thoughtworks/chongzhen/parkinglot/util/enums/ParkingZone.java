package com.thoughtworks.chongzhen.parkinglot.util.enums;

import java.util.Optional;

public enum ParkingZone {
    FIRST_FLOOR,
    SECOND_FLOOR;

    public ParkingZone next() {
        switch (this) {
            case FIRST_FLOOR:
                return SECOND_FLOOR;
            case SECOND_FLOOR:
                return FIRST_FLOOR;
            default:
                return null;
        }
    }
}
