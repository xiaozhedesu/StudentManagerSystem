package test.java.model;

import main.java.exception.InvalidDateException;
import main.java.exception.InvalidEMallException;
import main.java.exception.InvalidTelephoneException;
import main.java.exception.InvalidValueException;
import main.java.model.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    /* ---------- 1. 年龄 ---------- */
    @Test
    @DisplayName("setAge 正常值")
    void setAge_ok() throws Exception {
        Student stu = new Student();
        assertDoesNotThrow(() -> stu.setAge(18));
        assertEquals(18, stu.getAge());
    }

    @Test
    @DisplayName("setAge 负数抛 InvalidValueException")
    void setAge_negative() {
        Student stu = new Student();
        InvalidValueException ex = assertThrows(InvalidValueException.class,
                () -> stu.setAge(-5));
        assertTrue(ex.getMessage().contains("年龄不能小于0"));
    }

    /* ---------- 2. 性别 ---------- */
    @Test
    @DisplayName("setSex 合法值")
    void setSex_ok() throws Exception {
        Student stu = new Student();
        assertDoesNotThrow(() -> stu.setSex("女"));
        assertEquals("女", stu.getSex());
    }

    @Test
    @DisplayName("setSex 非法值抛异常")
    void setSex_bad() {
        Student stu = new Student();
        assertThrows(InvalidValueException.class, () -> stu.setSex("未知"));
    }

    /* ---------- 3. 生日 ---------- */
    @Test
    @DisplayName("setBirthday 合法日期")
    void setBirthday_ok() throws Exception {
        Student stu = new Student();
        stu.setBirthday("2000-12-15");
        assertEquals("2000-12-15", stu.getBirthdayString());
    }

    @Test
    @DisplayName("setBirthday 非法格式抛 InvalidDateException")
    void setBirthday_bad() {
        Student stu = new Student();
        InvalidDateException ex = assertThrows(InvalidDateException.class,
                () -> stu.setBirthday("2000-13-45"));
        assertTrue(ex.getMessage().contains("不是一个合格的日期字符串"));
    }

    /* ---------- 4. 手机 ---------- */
    @Test
    @DisplayName("setTelephone 合法手机号")
    void setTelephone_ok() throws Exception {
        Student stu = new Student();
        stu.setTelephone("13800138000");
        assertEquals("13800138000", stu.getTelephone());
    }

    @Test
    @DisplayName("setTelephone 非法号段抛 InvalidTelephoneException")
    void setTelephone_bad() {
        Student stu = new Student();
        assertThrows(InvalidTelephoneException.class,
                () -> stu.setTelephone("12800138000")); // 第二位 2 非法
    }

    /* ---------- 5. 邮箱 ---------- */
    @Test
    @DisplayName("setEmail 合法邮箱")
    void setEmail_ok() throws Exception {
        Student stu = new Student();
        stu.setEmail("tom.li+tag@example.com");
        assertEquals("tom.li+tag@example.com", stu.getEmail());
    }

    @Test
    @DisplayName("setEmail 非法格式抛 InvalidEMallException")
    void setEmail_bad() {
        Student stu = new Student();
        assertThrows(InvalidEMallException.class, () -> stu.setEmail("abc@"));
    }

    /* ---------- 6. 序列化/反序列化 ---------- */
    @Test
    @DisplayName("Serializable 一轮后内容不变")
    void serialization_roundTrip() throws Exception {
        Student src = new Student(
                "20231145141",
                "Alice",
                20,
                "女",
                "2002-06-18",
                "13911112222",
                "alice@qq.com");

        // 写
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(src);
        }

        // 读
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        Student dst;
        try (ObjectInputStream ois = new ObjectInputStream(bis)) {
            dst = (Student) ois.readObject();
        }

        // 断言
        assertEquals(src.getStudentId(), dst.getStudentId());
        assertEquals(src.getName(), dst.getName());
        assertEquals(src.getAge(), dst.getAge());
        assertEquals(src.getSex(), dst.getSex());
        assertEquals(src.getBirthdayString(), dst.getBirthdayString());
        assertEquals(src.getTelephone(), dst.getTelephone());
        assertEquals(src.getEmail(), dst.getEmail());
    }

    /* ---------- 7. 学号 ---------- */
    @Test
    @DisplayName("setStudentId 11位数字通过")
    void setStudentId_ok() throws Exception {
        Student stu = new Student();
        stu.setStudentId("20231345678");          // 11 位数字
        assertEquals("20231345678", stu.getStudentId());
    }

    @Test
    @DisplayName("setStudentId 非11位抛 InvalidValueException")
    void setStudentId_lengthBad() {
        Student stu = new Student();
        InvalidValueException ex = assertThrows(
                InvalidValueException.class,
                () -> stu.setStudentId("1234567890")); // 10 位
        assertTrue(ex.getMessage().contains("学号需要为11位数字"));
    }

    @Test
    @DisplayName("setStudentId 含字母抛 InvalidValueException")
    void setStudentId_notDigit() {
        Student stu = new Student();
        assertThrows(InvalidValueException.class,
                () -> stu.setStudentId("2023A123456"));
    }

    @Test
    @DisplayName("setStudentId null 抛 InvalidValueException")
    void setStudentId_null() {
        Student stu = new Student();
        assertThrows(InvalidValueException.class,
                () -> stu.setStudentId(null));
    }
}