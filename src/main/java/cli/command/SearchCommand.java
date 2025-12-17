package main.java.cli.command;

import main.java.exception.InvalidValueException;
import main.java.model.Student;
import main.java.service.StudentService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Stream;

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

        // 无参
        if (params == NO_PARAM) {
            showStudentsInfo(students.values());
            return;
        }

        // 参数不够2个
        if (params.length < 2) {
            throw new InvalidValueException("""
                    search指令需要2个参数：<fieldName> <value>
                    fieldName: 查询使用的字段名，比如：id->学号；name->姓名；
                    value: 查询使用的值，不传值则无法查询。
                    另外，当search指令不传参数时，代表查询全部。""");
        }

        // 不按字段查询的排序版
        if ("sortBy".equals(params[0])) {
            showStudentsInfo(students.values().stream()
                    .sorted(parseComparator(params[1]))
                    .toList());
            return;
        }

        // 按字段查询
        String fieldName = params[0];
        String value = params[1];
        Stream<Student> stream = switch (fieldName) {
            case "id" -> Stream.ofNullable(students.get(value));
            case "name" -> students.values().stream()
                    .filter(student -> student.getName().equals(value));
            case "sex" -> students.values().stream()
                    .filter(student -> student.getSex().equals(value));
            default -> throw new InvalidValueException("不支持的字段查询：" + fieldName);
        };

        // 按字段查找的排序
        if (params.length >= 4 && "sortBy".equals(params[2])) {
            stream = stream.sorted(parseComparator(params[3]));
        }

        // 按字段查找的输出
        showStudentsInfo(stream.toList());
    }

    /**
     * 复用遍历展示学生信息方法
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

    /**
     * 提供给流排序选择排序规则
     *
     * @param key 字段名
     * @return Comparator对象
     */
    private Comparator<Student> parseComparator(String key) {
        return switch (key) {
            case "name" -> Comparator.comparing(Student::getName);
            case "sex" -> Comparator.comparing(Student::getSex);
            case "birthday" -> Comparator.comparing(student -> LocalDate.parse(student.getBirthdayString()));
            default -> Comparator.comparing(Student::getStudentId);
        };
    }
}
