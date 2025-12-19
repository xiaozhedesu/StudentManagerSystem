package club.xiaozhe.exception;

public class StudentNotFoundException extends StudentSystemBusinessException {
    public StudentNotFoundException() {
        super("找不到学生！");
    }

    public StudentNotFoundException(String message) {
        super("找不到学生：" + message);
    }
}
