package main.java.exception;

public class StudentSystemBusinessException extends Exception {
    public StudentSystemBusinessException() {
        super();
    }

    public StudentSystemBusinessException(String message) {
        super(message);
    }

    public StudentSystemBusinessException(Throwable cause) {
        super(cause);
    }

    public StudentSystemBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
