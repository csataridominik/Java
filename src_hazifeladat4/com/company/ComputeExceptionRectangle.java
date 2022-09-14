package com.company;

public class ComputeExceptionRectangle extends ComputeException{
    public ComputeExceptionRectangle(String errorMessage) {
        super(errorMessage);
        System.err.println(errorMessage + "  Rectangle cannot be computed.");

    }
}
