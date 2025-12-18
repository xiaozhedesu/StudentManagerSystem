package main.java.cli.command;

import main.java.exception.InvalidValueException;
import main.java.exception.StudentSystemBusinessException;
import main.java.model.Student;
import main.java.service.StudentService;

import java.util.Collection;

public class ChangeCommand extends AbstractCommand implements Command {
    public ChangeCommand(StudentService studentService, String input) {
        super(studentService, input);
    }

    @Override
    public void execute() throws StudentSystemBusinessException {
        if (params.length < 4) {
            throw new InvalidValueException("参数不足：需要4个参数！");
        }

        // 参数合法性由调用的方法保证
        Collection<Student> students = studentService.getStudentListBy(params[0], params[1]);

        if (students.isEmpty()) {
            System.out.println("没有学生信息，不做修改。");
            return;
        }

        // 大作业就不写二次确认了，数据不重要
        int success = 0, fail = 0;
        for (Student student : students) {
            try {
                success += studentService.changeStudent(student, params[2], params[3]) ? 1 : 0;
            } catch (StudentSystemBusinessException e) {
                System.out.println("❌ 修改失败：" + e.getMessage());
                fail += 1;
            }
        }
        System.out.printf("%s 修改成功 %d 项，失败 %d 项。\n", fail == 0 ? "✅" : "❌", success, fail);

    }
}
