package com.infosys.enums;

public enum CustomerAction {
    PUT("put"),
    TAKE("take"),
    NOT_ABLE_TO_IDENTIFY("cannot identify action");

    CustomerAction(String message) {
        System.out.println(message);
    }
}
