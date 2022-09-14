package hu.ppke.itk.khf05;

public class UnknownOrderException extends Exception{
    public UnknownOrderException(String errorMessage) {
        super(errorMessage);
    }
}
