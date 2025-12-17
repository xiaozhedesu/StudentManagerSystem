package main.java.exception;

public class InvalidTelephoneException extends InvalidValueException {
    public InvalidTelephoneException() {
        super("电话格式错误！");
    }

    public InvalidTelephoneException(String message) {
        super("电话格式错误：" + message);
    }
}
