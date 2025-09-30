package Exceptions;

public class NotUniqeNameException extends IllegalArgumentException {
    public NotUniqeNameException(String message) {
        super(message);
    }
}
