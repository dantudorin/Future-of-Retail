package com.infosys.exception;

public class FaceNotExistsException extends Exception{
    public FaceNotExistsException() {
        super("Face not found!"); //de facut advice!
    }
}
