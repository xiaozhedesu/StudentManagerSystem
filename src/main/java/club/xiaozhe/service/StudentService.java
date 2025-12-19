package club.xiaozhe.service;

import club.xiaozhe.exception.InvalidValueException;
import club.xiaozhe.exception.StudentIdAlreadyExistsException;
import club.xiaozhe.exception.StudentNotFoundException;
import club.xiaozhe.exception.StudentSystemBusinessException;
import club.xiaozhe.model.Student;
import club.xiaozhe.util.DataCheck;

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
     * 确保学生对象存在于列表中
     *
     * @param student 学生对象
     * @throws StudentSystemBusinessException 不存在此学生数据时抛出
     */
    private void ensureStudentInList(Student student) throws StudentSystemBusinessException {
        if (student == null) {
            throw new InvalidValueException("学生信息为空！");
        }

        if (!students.containsKey(student.getStudentId())) {
            throw new StudentNotFoundException("id: " + student.getStudentId());
        }

        if (!students.get(student.getStudentId()).equals(student)) {
            throw new StudentSystemBusinessException("学生信息匹配失败：" + student + "与列表中的学生数据不一致！");
        }
    }

    /**
     * 根据学生对象删除学生信息
     *
     * @param student 学生对象
     * @throws StudentSystemBusinessException 发生业务错误时抛出
     */
    public boolean deleteStudent(Student student) throws StudentSystemBusinessException {
        ensureStudentInList(student);

        students.remove(student.getStudentId());

        return true;
    }

    /**
     * 按照给定键值对修改传入的学生信息
     *
     * @param student   学生对象
     * @param fieldName 阻断名
     * @param value     值
     * @return 修改成功返回true
     * @throws StudentSystemBusinessException 发生业务错误时抛出
     */
    public boolean changeStudent(Student student, String fieldName, String value) throws StudentSystemBusinessException {
        ensureStudentInList(student);

        Student newStudent = new Student(student);
        switch (fieldName) {
            case "name" -> newStudent.setName(value);
            case "age" -> {
                if (!DataCheck.isNumString(value)) {
                    throw new InvalidValueException("年龄不是数字！");
                }
                newStudent.setAge(Integer.parseInt(value));
            }
            case "sex" -> newStudent.setSex(value);
            case "birthday" -> newStudent.setBirthday(value);
            case "telephone" -> newStudent.setTelephone(value);
            case "email" -> newStudent.setEmail(value);
            case "id" -> throw new InvalidValueException("不支持修改id！");
            default -> throw new StudentSystemBusinessException("不支持的操作，请重试。");
        }

        students.remove(student.getStudentId());
        students.put(newStudent.getStudentId(), newStudent);

        return true;
    }
}
