package club.xiaozhe.exception;

public class StudentIdAlreadyExistsException extends StudentSystemBusinessException {
    public StudentIdAlreadyExistsException() {
        super("已存在相同学号的学生信息！");
    }

    public StudentIdAlreadyExistsException(String message) {
        super("已存在相同学号的学生信息：" + message);
    }
}
