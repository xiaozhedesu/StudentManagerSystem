package club.xiaozhe.cli.command;

import club.xiaozhe.exception.InvalidValueException;
import club.xiaozhe.exception.StudentSystemBusinessException;
import club.xiaozhe.model.Student;
import club.xiaozhe.service.StudentService;

import java.util.Collection;

public class DeleteCommand extends AbstractCommand implements Command {
    public DeleteCommand(StudentService studentService, String input) {
        super(studentService, input);
    }

    @Override
    public void execute() throws StudentSystemBusinessException {
        // 无参
        if (params == NO_PARAM) {
            throw new InvalidValueException("不支持全部删除！");
        }

        // 参数不够2个
        if (params.length < 2) {
            throw new InvalidValueException("""
                    delete指令需要2个参数：<fieldName> <value>
                    fieldName: 删除使用的字段名，比如：id->学号；name->姓名；
                    value: 删除使用的值，不传值则无法删除。""");
        }

        Collection<Student> students = studentService.getStudentListBy(params[0], params[1]);
        if (students.isEmpty()) {
            System.out.println("❌ 未找到学生信息，不执行删除操作。");
            return;
        }

        // 大作业就不写二次确认了，数据不重要
        int success = 0, fail = 0;
        for (Student student : students) {
            try {
                success += studentService.deleteStudent(student) ? 1 : 0;
            } catch (StudentSystemBusinessException e) {
                System.out.println("❌ 删除失败：" + e.getMessage());
                fail += 1;
            }
        }
        System.out.printf("%s 删除成功 %d 项，失败 %d 项。\n", fail == 0 ? "✅" : "❌", success, fail);
    }
}
