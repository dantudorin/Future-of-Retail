package com.infosys.gate.exception;

public class FaceNotExistsException extends Exception{
    public FaceNotExistsException() {
        super("Face not found!");
    }
}
