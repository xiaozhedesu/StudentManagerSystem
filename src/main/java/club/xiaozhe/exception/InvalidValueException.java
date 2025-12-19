package club.xiaozhe.exception;

/**
 * 统一业务字段值非法异常
 */
public class InvalidValueException extends StudentSystemBusinessException {

    public InvalidValueException() {
        super();
    }

    public InvalidValueException(String message) {
        super(message);
    }

    public InvalidValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidValueException(Throwable cause) {
        super(cause);
    }
}