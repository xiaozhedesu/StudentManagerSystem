package main.java.cli.command;

import main.java.exception.StudentSystemBusinessException;
import main.java.service.StudentService;

public class HelpCommand extends AbstractCommand implements Command {
    public HelpCommand(StudentService studentService, String input) {
        super(studentService, input);
    }

    /* ---------- 常量池 ---------- */
    private static final String HELP_DEFAULT = """
            help 查看帮助
            exit 退出
            save 保存学生数据到本地
            load 从本地加载学生数据
            search 搜索学生信息
            add 添加学生信息
            delete 删除学生信息
            change 修改学生信息
            
            可以使用help (command) 查看更多帮助""";

    private static final String HELP_HELP = """
            help (command) 查看帮助
            () 表示可选参数，<> 表示必填参数""";

    private static final String HELP_SEARCH = """
            search 搜索学生信息
            search (sortBy) (sortFieldName) 搜索全部学生信息
            search <fieldName> <value> (sortBy) (sortFieldName) 搜索对应字段值为value的所有学生信息
            支持的字段：
            fieldName: id, name
            sortBy是关键字，指令中必须一致
            sortFieldName: name, sex, birthday""";

    private static final String HELP_ADD = """
            add (studentData) 添加学生信息
            如果不输入studentData则会在新一行显示提示信息再接收信息
            studentData对应顺序：（学号，姓名，年龄，性别，生日，电话，email）""";

    private static final String HELP_DELETE = """
            delete 删除学生信息
            delete <fieldName> <value> 将对应字段值为value的所有学生信息都删除
            支持的字段：
            fieldName: id, name""";

    private static final String HELP_CHANGE = """
            change 修改学生信息
            change <limitFieldName> <limitValue> <changeFieldName> <changeValue>
            limit: 筛选对应字段值为value的所有学生信息
            change: 将筛选后的学生信息的所有对应字段值改为value
            支持的字段：
            limitFieldName: id, name
            changeFieldName: name, age, sex, birthday, telephone, email""";

    private static final String HELP_LOAD = """
            load 从本地加载学生数据""";

    private static final String HELP_SAVE = """
            save 保存学生数据到本地""";

    @Override
    public void execute() throws StudentSystemBusinessException {
        if (params == NO_PARAM) {
            System.out.println(HELP_DEFAULT);
            return;
        }

        String helpMessage = switch (params[0]) {
            case "help"   -> HELP_HELP;
            case "search" -> HELP_SEARCH;
            case "add"    -> HELP_ADD;
            case "delete" -> HELP_DELETE;
            case "change" -> HELP_CHANGE;
            case "load"   -> HELP_LOAD;
            case "save"   -> HELP_SAVE;
            default       -> HELP_DEFAULT;
        };

        System.out.println(helpMessage);
    }
}
