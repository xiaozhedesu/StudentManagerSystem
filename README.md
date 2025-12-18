# StudentManagerSystem

学校的任务罢了。

## 学生信息管理系统

### 题目
学生信息包括：学号，姓名，年龄，性别，出生年月，地址，电话，E-mail 等。

试设计一学生信息管理系统，使之能提供以下功能：
- [x] 学生信息录入功能（学生信息用文件保存）--- 输入
- [x] 学生信息浏览功能 --- 输出
- [x] 查询、排序功能 --- 算法
- [x] 按学号查询
- [x] 按姓名查询
- [x] 学生信息的删除与修改（可选项）
- [x] 给命令行程序添加help指令

### 总结

至此，工程已毕，只待老师让不让我过了。

还是总结一下吧：这个学生管理系统是一个命令行程序，通过敲指令的方式查看和修改程序中通过HashMap保存的学生信息。对于用户输入中的问题，通过抛出异常并在CommandHandler中捕获的方式终止指令并显示问题。每次启动程序都会从本地的student.txt文件中读取数据，执行exit指令退出的时候会将文件保存在student.txt中。

### 工程结构

- main.java java程序包
    - cli 命令行程序
        - command 指令包，里面有各种对应指令的执行类
        - CommandHandler 处理用户输入的类
        - Main 启动
    - exception 异常类
    - model 数据结构模型
        - Student 学生信息结构类，支持java序列化
    - service 提供通用的操作方法，适配不同客户端
        - StudentService 学生信息服务，增删改查以及保存加载的核心实现 
    - util 工具函数
        - DataCheck 数据校验相关的工具类
        - DateTool 处理时间的工具类
- test.java 单元测试包
    - cli.command
        - AddCommandTest 测试添加功能正常性
    - model
        - StudentTest 测试学生类中字段值校验的正确性