package main.java.cli.command;

import main.java.model.Student;
import main.java.service.StudentService;

import java.util.ArrayList;

/**
 * 搜索指令
 */
public class SearchCommand extends AbstractCommand implements Command {
    public SearchCommand(StudentService studentService) {
        super(studentService);
    }

    @Override
    public void execute() {
        ArrayList<Student> students = studentService.getStudentList();
        if (students.isEmpty()) {
            System.out.println("没有学生信息。");
            return;
        }
        System.out.println("一共有" + students.size() + "个学生信息：");
        for (Student student : students) {
            System.out.println(student);
        }
    }
}
