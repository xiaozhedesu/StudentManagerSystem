package club.xiaozhe.model;

import club.xiaozhe.exception.InvalidDateException;
import club.xiaozhe.exception.InvalidEmailException;
import club.xiaozhe.exception.InvalidTelephoneException;
import club.xiaozhe.exception.InvalidValueException;
import club.xiaozhe.util.DataCheck;
import club.xiaozhe.util.DateTool;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 学生数据结构类，包含一些数据校验
 */
public class Student implements Serializable {
    @Serial
    private static final long serialVersionUID = 1145141919810L;
    private String studentId;
    private String name;
    private int age;
    private String sex;
    private Date birthday;
    private String telephone;
    private String email;

    public Student() {
    }

    /**
     * 全参构造 —— 全部字段通过 setter 赋值，自带校验
     */
    public Student(String studentId,
                   String name,
                   int age,
                   String sex,
                   String birthday,
                   String telephone,
                   String email) throws InvalidValueException {
        setStudentId(studentId);
        setName(name);
        setAge(age);
        setSex(sex);
        setBirthday(birthday);
        setTelephone(telephone);
        setEmail(email);
    }

    /**
     * 复制构造 —— 通过别的学生对象构建新的对象
     */
    public Student(Student other) throws InvalidValueException {
        this(
                other.getStudentId(),
                other.getName(),
                other.getAge(),
                other.getSex(),
                other.getBirthdayString(),
                other.getTelephone(),
                other.getEmail()
        );
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) throws InvalidValueException {
        if (!DataCheck.isValidId(studentId)) {
            throw new InvalidValueException("学号需要为11位数字！");
        }
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) throws InvalidValueException {
        if (age < 0) {
            throw new InvalidValueException("年龄不能小于0！");
        }
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) throws InvalidValueException {
        if (!DataCheck.isValidSex(sex)) {
            throw new InvalidValueException("性别只能是男、女或保密");
        }
        this.sex = sex;
    }

    public Date getBirthday() {
        return new Date(birthday.getTime());
    }

    public void setBirthday(String birthday) throws InvalidDateException {
        if (!DataCheck.isValidDate(birthday)) {
            throw new InvalidDateException(birthday + "不是一个合格的日期字符串（需要yyyy-MM-dd）或日期不合法！");
        }
        this.birthday = DateTool.stringToDate(birthday);
    }

    public String getBirthdayString() {
        return DateTool.dateToString(birthday);
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) throws InvalidTelephoneException {
        if (!DataCheck.isValidTelephone(telephone)) {
            throw new InvalidTelephoneException("电话需要是中国大陆手机号，11 位，1 开头，第 2 位 3-9");
        }
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws InvalidEmailException {
        if (!DataCheck.isValidEmail(email)) {
            throw new InvalidEmailException("示例：yourEmailName@example.com");
        }
        this.email = email;
    }

    /**
     * 检查对象中是否含有空值字段
     *
     * @return 有至少一项为空返回true， 否则返回false
     */
    public boolean hasNullField() {
        return studentId == null
                || name == null
                || sex == null
                || telephone == null
                || email == null
                || birthday == null;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", birthday='" + getBirthdayString() + '\'' +
                ", telephone='" + telephone + '\'' +
                ", Email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Student other)) return false;
        return Objects.equals(studentId, other.studentId) &&
                Objects.equals(name, other.name) &&
                age == other.age &&
                Objects.equals(sex, other.sex) &&
                Objects.equals(getBirthdayString(), other.getBirthdayString()) &&
                Objects.equals(telephone, other.telephone) &&
                Objects.equals(email, other.email);
    }
}