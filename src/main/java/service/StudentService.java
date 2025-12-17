package main.java.service;

import main.java.exception.InvalidValueException;
import main.java.exception.StudentIdAlreadyExistsException;
import main.java.model.Student;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

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
     * @return 保存了学生数据的新数组
     */
    public HashMap<String, Student> getStudentList() {
        return new HashMap<>(students);
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
}
