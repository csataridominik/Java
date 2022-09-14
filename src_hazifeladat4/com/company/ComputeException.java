package com.company;

public class ComputeException extends Exception{

    public ComputeException(String errorMessage) {
        super(errorMessage);
        System.err.println(errorMessage);
    }

}
