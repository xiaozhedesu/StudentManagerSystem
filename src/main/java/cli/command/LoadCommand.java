package main.java.cli.command;

import main.java.exception.StudentSystemBusinessException;
import main.java.service.StudentService;

import java.io.IOException;

public class LoadCommand extends AbstractCommand implements Command {
    public LoadCommand(StudentService studentService) {
        super(studentService);
    }

    @Override
    public void execute() throws StudentSystemBusinessException {
        System.out.println("正在读取数据...");
        try {
            studentService.loadStudentList();
            System.out.println("数据读取成功。");
        } catch (IOException e) {
            // 写成这样是为了不改接口异常定义
            throw new StudentSystemBusinessException("发生IO错误：" + e.getMessage());
        }
    }
}
