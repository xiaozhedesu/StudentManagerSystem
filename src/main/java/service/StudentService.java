package main.java.service;

import main.java.exception.InvalidValueException;
import main.java.exception.StudentIdAlreadyExistsException;
import main.java.exception.StudentNotFoundException;
import main.java.exception.StudentSystemBusinessException;
import main.java.model.Student;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentService {
    private static final Path STUDENT_DATA_FILE = Paths.get("./student.txt");
    private HashMap<String, Student> students;

    /**
     * 从指定文件读取学生数据
     *
     * @throws IOException 发生IO错误时抛出
     */
    public void saveStudentList() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STUDENT_DATA_FILE.toFile()))) {
            oos.writeObject(students);
        }
    }

    /**
     * 存储学生数据到指定区域
     *
     * @throws IOException 发生IO错误时抛出
     */
    @SuppressWarnings("unchecked")
    public void loadStudentList() throws IOException {
        if (!Files.exists(STUDENT_DATA_FILE)) {
            this.students = new HashMap<>();
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STUDENT_DATA_FILE.toFile()))) {
            this.students = (HashMap<String, Student>) ois.readObject();
        } catch (ClassNotFoundException e) {
            this.students = new HashMap<>();
        } catch (IOException e) {
            // 保证发生IO错误时不会被调用students导致NPE
            this.students = new HashMap<>();
            throw e;
        }
    }

    /**
     * 获取学生数据
     *
     * @return 保存了学生数据的新列表
     */
    public Collection<Student> getStudentList() {
        return students.values();
    }

    /**
     * 按特定字段获取学生数据
     *
     * @param fieldName 字段名
     * @param value     值
     * @return 筛选后的学生数据列表
     * @throws InvalidValueException 当字段名不支持的时候抛出
     */
    public Collection<Student> getStudentListBy(String fieldName, String value) throws InvalidValueException {
        if (fieldName == null) throw new InvalidValueException("字段名为空！");
        if (value == null) throw new InvalidValueException("值为空！");
        return (switch (fieldName) {
            case "id" -> Stream.ofNullable(students.get(value));
            case "name" -> students.values().stream()
                    .filter(student -> student.getName().equals(value));
            case "sex" -> students.values().stream()
                    .filter(student -> student.getSex().equals(value));
            default -> throw new InvalidValueException("不支持的字段查询：" + fieldName);
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * 添加学生数据
     *
     * @param student 学生对象
     * @throws StudentIdAlreadyExistsException 当列表中存在对应学号的学生时抛出
     * @throws InvalidValueException           当发生数值错误时抛出
     */
    public void addStudent(Student student) throws StudentIdAlreadyExistsException, InvalidValueException {
        if (student == null) {
            throw new InvalidValueException("学生信息为空！");
        }
        if (student.hasNullField()) {
            throw new InvalidValueException("学生信息中含有空值：" + student);
        }
        if (students.containsKey(student.getStudentId())) {
            throw new StudentIdAlreadyExistsException(student.getStudentId());
        }

        students.put(student.getStudentId(), student);
    }

    /**
     * 根据学号删除学生信息
     *
     * @param student 学生对象
     * @throws StudentSystemBusinessException 发生业务错误时抛出
     */
    public boolean deleteStudentById(Student student) throws StudentSystemBusinessException {
        if (student == null) {
            throw new InvalidValueException("学生信息为空！");
        }

        if (!students.containsKey(student.getStudentId())) {
            throw new StudentNotFoundException("id: " + student.getStudentId());
        }

        if (students.get(student.getStudentId()).equals(student)) {
            students.remove(student.getStudentId());
        } else {
            // 只要不给用户自己构造，一般不会触发。
            throw new StudentSystemBusinessException("删除失败：学生信息匹配失败。");
        }

        return true;
    }
}
