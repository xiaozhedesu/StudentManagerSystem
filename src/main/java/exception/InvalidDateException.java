package main.java.exception;

public class InvalidDateException extends InvalidValueException {
    public InvalidDateException() {
        super("日期格式错误！");
    }

    public InvalidDateException(String message) {
        super("日期格式错误：" + message);
    }
}
