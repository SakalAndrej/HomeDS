package at.htl.exceptions;

public class NoConnectionException extends Exception {

    public NoConnectionException() { }

    public NoConnectionException(String message) {
        super(message);
    }

    public NoConnectionException (Throwable cause) {
        super (cause);
    }

    public NoConnectionException (String message, Throwable cause) {
        super (message, cause);
    }
}
