package main.java.exception;

public class InvalidEmailException extends InvalidValueException {
    public InvalidEmailException() {
        super("Email格式错误！");
    }

    public InvalidEmailException(String message) {
        super("Email格式错误：" + message);
    }
}
