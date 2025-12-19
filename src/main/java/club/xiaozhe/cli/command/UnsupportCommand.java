package club.xiaozhe.cli.command;

public class UnsupportCommand implements Command {
    @Override
    public void execute() {
        System.out.println("不支持的操作，请重新输入。");
    }
}
