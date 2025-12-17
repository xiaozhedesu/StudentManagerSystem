package main.java.cli.command;

import main.java.exception.StudentSystemBusinessException;
import main.java.service.StudentService;

import java.io.IOException;

public class SaveCommand extends AbstractCommand implements Command{
    public SaveCommand(StudentService studentService) {
        super(studentService);
    }

    @Override
    public void execute() throws StudentSystemBusinessException {
        try {
            studentService.saveStudentList();
        } catch (IOException e) {
            // 写成这样是为了不改接口异常定义
            throw new StudentSystemBusinessException("发生IO错误：" + e.getMessage());
        }
    }
}
