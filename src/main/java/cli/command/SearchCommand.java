package main.java.cli.command;

import main.java.exception.InvalidValueException;
import main.java.model.Student;
import main.java.service.StudentService;

import java.util.Collection;
import java.util.HashMap;

/**
 * 搜索指令
 */
public class SearchCommand extends AbstractCommand implements Command {
    public SearchCommand(StudentService studentService, String input) {
        super(studentService, input);
    }

    @Override
    public void execute() throws InvalidValueException {
        HashMap<String, Student> students = studentService.getStudentList();
        if (params == NO_PARAM) {
            showStudentsInfo(students.values());
            return;
        }

        if (params.length < 2) {
            throw new InvalidValueException("""
                    search指令需要2个参数：<fieldName> <value>
                    fieldName: 查询使用的字段名，比如：id->学号；name->姓名；
                    value: 查询使用的值，不传值则无法查询。
                    另外，当search指令不传参数时，代表查询全部。""");
        }

        String fieldName = params[0];
        if ("id".equals(fieldName)) {
            String id = params[1];
            if (students.containsKey(id)) {
                System.out.println(students.get(id));
            } else {
                System.out.println("未找到id为 " + id + " 的学生。");
            }
        } else if ("name".equals(fieldName)) {
            String name = params[1];
            Collection<Student> filtered_students = students.values().stream()
                    .filter(student -> student.getName().equals(name))
                    .toList();
            showStudentsInfo(filtered_students);
        } else {
            throw new InvalidValueException("不支持的字段查询：" + fieldName);
        }
    }

    /**
     * 服用遍历展示学生信息方法
     *
     * @param students 学生列表
     */
    private void showStudentsInfo(Collection<Student> students) {
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
