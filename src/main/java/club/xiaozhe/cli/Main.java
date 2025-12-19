package club.xiaozhe.cli;

import club.xiaozhe.exception.ExitException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CommandHandler handler = new CommandHandler();
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.print("admin> ");
                String in = scanner.nextLine();
                handler.handler(in);
            }
        } catch (ExitException e) {
            System.out.println("系统关闭。");
        }
    }
}