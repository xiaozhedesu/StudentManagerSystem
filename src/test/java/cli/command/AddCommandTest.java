package test.java.cli.command;

import main.java.cli.command.AddCommand;
import main.java.exception.InvalidValueException;
import main.java.exception.StudentIdAlreadyExistsException;
import main.java.model.Student;
import main.java.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 纯 JUnit 5 测试 AddCommand，不引入任何额外依赖
 */
class AddCommandTest {

    private StubStudentService stubService;
    private AddCommand addCommand;

    @BeforeEach
    void setUp() {
        stubService = new StubStudentService();
        addCommand = new AddCommand(stubService);
    }

    /* ----------  辅助方法  ---------- */
    private void mockInput(String line) {
        InputStream in = new ByteArrayInputStream(line.getBytes());
        System.setIn(in);
    }

    /* ----------  合法场景  ---------- */
    @Test
    @DisplayName("全部字段合法 -> 成功添加")
    void testAddSuccess() throws Exception {
        mockInput("12345678901 张三 18 男 2005-06-15 13800138000 zhangsan@foo.com");

        addCommand.execute();

        assertEquals(1, stubService.getDb().size());
        Student s = stubService.getDb().get(0);
        assertEquals("12345678901", s.getStudentId());
        assertEquals("张三", s.getName());
        assertEquals(18, s.getAge());
        assertEquals("男", s.getSex());
        assertEquals(LocalDate.parse("2005-06-15"), LocalDate.parse(s.getBirthdayString()));
        assertEquals("13800138000", s.getTelephone());
        assertEquals("zhangsan@foo.com", s.getEmail());
    }

    /* ----------  学号 11 位数字  ---------- */
    @Test
    @DisplayName("学号不是11位数字 -> 抛 InvalidValueException")
    void testStudentIdNot11Digits() {
        mockInput("123 张三 18 男 2005-06-15 13800138000 zhangsan@foo.com");

        InvalidValueException ex = assertThrows(InvalidValueException.class,
                () -> addCommand.execute());
        assertTrue(ex.getMessage().contains("学号需要为11位数字！"));
    }

    /* ----------  生日格式  ---------- */
    @Test
    @DisplayName("生日格式非法 -> 抛 InvalidValueException")
    void testBirthdayFormatWrong() {
        mockInput("12345678901 张三 18 男 2005/06/15 13800138000 zhangsan@foo.com");

        InvalidValueException ex = assertThrows(InvalidValueException.class,
                () -> addCommand.execute());
        assertTrue(ex.getMessage().contains("不是一个合格的日期字符串（需要YYYY-MM-DD）"));
    }

    @Test
    @DisplayName("生日为非法日期 -> 抛 InvalidValueException")
    void testBirthdayInvalidDate() {
        mockInput("12345678901 张三 18 男 2005-02-30 13800138000 zhangsan@foo.com");

        InvalidValueException ex = assertThrows(InvalidValueException.class,
                () -> addCommand.execute());
        assertTrue(ex.getMessage().contains("不是一个合格的日期字符串（需要YYYY-MM-DD）"));
    }

    /* ----------  性别枚举  ---------- */
    @Test
    @DisplayName("性别不在允许范围 -> 抛 InvalidValueException")
    void testSexNotAllowed() {
        mockInput("12345678901 张三 18 未知 2005-06-15 13800138000 zhangsan@foo.com");

        InvalidValueException ex = assertThrows(InvalidValueException.class,
                () -> addCommand.execute());
        assertTrue(ex.getMessage().contains("性别只能是男、女或保密"));
    }

    /* ----------  email 格式  ---------- */
    @Test
    @DisplayName("email 格式非法 -> 抛 InvalidValueException")
    void testEmailFormatWrong() {
        mockInput("12345678901 张三 18 男 2005-06-15 13800138000 zhangsan#foo.com");

        InvalidValueException ex = assertThrows(InvalidValueException.class,
                () -> addCommand.execute());
        assertTrue(ex.getMessage().contains("示例：yourEmallName@example.com"));
    }

    /* ----------  参数个数  ---------- */
    @Test
    @DisplayName("参数个数 ≠7 -> 抛 InvalidValueException")
    void testParamCountWrong() {
        mockInput("12345678901 张三 18 男 2005-06-15 13800138000"); // 缺 email

        InvalidValueException ex = assertThrows(InvalidValueException.class,
                () -> addCommand.execute());
        assertTrue(ex.getMessage().contains("传入参数过多或过少"));
    }

    /* ----------  学号重复  ---------- */
    @Test
    @DisplayName("学号已存在 -> 抛 StudentIdAlreadyExistsException")
    void testStudentIdExists() throws Exception {
        stubService.addStudent(new Student("12345678901", "旧生", 20, "保密",
                "2004-01-01", "13900000000", "old@foo.com"));

        mockInput("12345678901 张三 18 男 2005-06-15 13800138000 zhangsan@foo.com");

        assertThrows(StudentIdAlreadyExistsException.class,
                () -> addCommand.execute());
    }

    /* ----------  手工 Stub 的 Service ---------- */
    private static class StubStudentService extends StudentService {
        private final List<Student> db = new ArrayList<>();

        @Override
        public void addStudent(Student s) throws StudentIdAlreadyExistsException {
            if (db.stream().anyMatch(stu -> stu.getStudentId().equals(s.getStudentId()))) {
                throw new StudentIdAlreadyExistsException(s.getStudentId());
            }
            db.add(s);
        }

        public List<Student> getDb() {
            return db;
        }
    }
}