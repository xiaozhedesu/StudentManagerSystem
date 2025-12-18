package main.java.cli;

import main.java.cli.command.*;
import main.java.exception.ExitException;
import main.java.exception.StudentSystemBusinessException;
import main.java.service.StudentService;

import java.io.IOException;

/**
 * 应用简单工厂模式的指令处理类，不考虑多线程实现
 */
public class CommandHandler {
    private static final StudentService studentService = new StudentService();

    /**
     * 初始化对象的时候初始化数据
     */
    public CommandHandler() {
        System.out.println("正在读取数据...");
        try {
            studentService.loadStudentList();
            System.out.println("数据读取成功。");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 退出系统的时候保存数据并传递信号给Main函数
     *
     * @throws ExitException 只传递退出信号的异常，调用一定抛出
     */
    private void exit() throws ExitException {
        System.out.println("正在存储数据...");
        try {
            studentService.saveStudentList();
            System.out.println("数据存储成功。");
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ExitException();
    }

    /**
     * 处理用户输入的函数
     *
     * @param input 用户输入
     * @throws ExitException 只传递退出信号的异常，在输入exit的时候抛出
     */
    public void handler(String input) throws ExitException {
        if ("exit".equals(input))
            exit();
        if (input.matches("\\s*"))
            return;

        String op = input.split("\\s+")[0];
        Command command = switch (op) {
            case "help" -> new HelpCommand(studentService, input);
            case "search" -> new SearchCommand(studentService, input);
            case "add" -> new AddCommand(studentService, input);
            case "delete" -> new DeleteCommand(studentService, input);
            case "change" -> new ChangeCommand(studentService, input);
            case "load" -> new LoadCommand(studentService);
            case "save" -> new SaveCommand(studentService);
            default -> new UnsupportCommand();
        };

        try {
            command.execute();
        } catch (StudentSystemBusinessException e) {
            System.out.println("❌ Error：" + e.getMessage());
        }
    }
}
