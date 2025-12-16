package main.java.service;

import main.java.model.Student;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class StudentService {
    private static final Path STUDENT_DATA_FILE = Paths.get("./student.txt");
    private ArrayList<Student> students;

    /**
     * 从指定文件读取学生数据
     * @throws IOException 发生IO错误时抛出
     */
    public void saveStudentList() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STUDENT_DATA_FILE.toFile()))) {
            oos.writeObject(students);
        }
    }

    /**
     * 存储学生数据到指定区域
     * @throws IOException 发生IO错误时抛出
     */
    @SuppressWarnings("unchecked")
    public void loadStudentList() throws IOException {
        if (!Files.exists(STUDENT_DATA_FILE)) {
            this.students = new ArrayList<>();
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STUDENT_DATA_FILE.toFile()))) {
            this.students = (ArrayList<Student>) ois.readObject();
        } catch (ClassNotFoundException e) {
            this.students = new ArrayList<>();
        }
    }
}
