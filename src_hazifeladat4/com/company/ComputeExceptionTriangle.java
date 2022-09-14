package com.company;

public class ComputeExceptionTriangle extends ComputeException{

    public ComputeExceptionTriangle(String errorMessage) {
        super(errorMessage);
        System.err.println(errorMessage + "  Triangle cannot be computed.");

    }

}
