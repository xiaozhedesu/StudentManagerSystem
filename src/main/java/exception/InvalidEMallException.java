package main.java.exception;

public class InvalidEMallException extends InvalidValueException {
    public InvalidEMallException() {
        super("Emall格式错误！");
    }

    public InvalidEMallException(String message) {
        super("Emall格式错误：" + message);
    }
}
