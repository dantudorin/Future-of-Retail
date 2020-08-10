package com.infosys.exception;

public class UserNotFoundException extends Exception{

    public UserNotFoundException() {
        super("User not found !");
    }
}
