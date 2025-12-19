package club.xiaozhe.cli.command;

import club.xiaozhe.exception.StudentSystemBusinessException;

/**
 * 指令接口，指令需要可运行
 */
public interface Command {
    void execute() throws StudentSystemBusinessException;
}
