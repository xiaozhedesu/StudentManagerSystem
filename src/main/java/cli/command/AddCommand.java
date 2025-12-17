package main.java.cli.command;

import main.java.exception.InvalidValueException;
import main.java.exception.StudentSystemBusinessException;
import main.java.model.Student;
import main.java.service.StudentService;
import main.java.util.DataCheck;

import java.util.Scanner;

public class AddCommand extends AbstractCommand implements Command {
    public AddCommand(StudentService studentService) {
        super(studentService);
    }

    @Override
    public void execute() throws StudentSystemBusinessException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("请在一行中输入学生的信息，不同的字段使用空格分隔。\n"
                + "对应顺序：（学号，姓名，年龄，性别，生日，电话，emall）");
        String input = scanner.nextLine();
        String[] params = input.split("\\s+");
        if (params.length != 7) {
            throw new InvalidValueException("传入参数过多或过少：应有 7 但是现在有 " + params.length + "！");
        }
        if (!DataCheck.isNumString(params[2])) {
            throw new InvalidValueException("年龄不是整数！");
        }
        Student student = new Student(
                params[0],                  // studentId  String
                params[1],                  // name       String
                Integer.parseInt(params[2]),// age        int
                params[3],                  // sex        String
                params[4],                  // birthday   String（yyyy-MM-dd）
                params[5],                  // telephone  String
                params[6]                   // email      String
        );
        studentService.addStudent(student); // 真正的添加操作
        System.out.println("✅ 添加成功！");
    }
}