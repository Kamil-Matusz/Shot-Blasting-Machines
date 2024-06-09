package com.example.api.model;

public enum OrderStateEnum {
    NEW(1),
    IN_PRODUCTION(2),
    TO_BE_CHECKED(3),
    TO_BE_CORRECTED(4),
    READY(5),
    COMPLETED(6);
    private final long value;

    OrderStateEnum(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public static OrderStateEnum fromValue(int value) {
        for (OrderStateEnum state : OrderStateEnum.values()) {
            if (state.value == value) {
                return state;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }
}
