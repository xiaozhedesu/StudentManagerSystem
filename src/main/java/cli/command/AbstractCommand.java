package main.java.cli.command;

import main.java.service.StudentService;

import java.util.Arrays;

/**
 * 抽象指令类，提供一些共有方法
 */
public abstract class AbstractCommand {
    protected final StudentService studentService;
    protected final String[] param;

    protected AbstractCommand(StudentService studentService) {
        this(studentService, "");
    }

    protected AbstractCommand(StudentService studentService, String input) {
        this.studentService = studentService;
        this.param = getParam(input);
    }

    /**
     * 获取用户输入中的参数，返回字符串数组
     *
     * @param input 用户输入
     * @return 参数字符串数组
     */
    private String[] getParam(String input) {
        String[] inputs = input.split("\\s+");
        if (inputs.length > 1) {
            return Arrays.copyOfRange(inputs, 1, inputs.length);
        }
        return new String[0];
    }
}
