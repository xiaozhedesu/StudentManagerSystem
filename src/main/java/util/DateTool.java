package main.java.util;

import main.java.exception.InvalidDateException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间转换工具
 */
public class DateTool {
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 它是一个不允许实例化的类
     */
    private DateTool() {
    }

    /**
     * Date对象转字符串（yyyy-MM-dd）
     *
     * @param date Date对象
     * @return yyyy-MM-dd格式的字符串
     */
    public static String dateToString(Date date) {
        LocalDateTime ldt = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        return ldt.format(fmt);
    }

    /**
     * 字符串转Date对象（yyyy-MM-dd）
     *
     * @param str 字符串
     * @return Date对象
     */
    public static Date stringToDate(String str) throws InvalidDateException {
        if (!DataCheck.isValidDate(str)) {
            throw new InvalidDateException("需要为yyyy-MM-dd！");
        }
        LocalDate ld = LocalDate.parse(str, fmt);
        return Date.from((ld.atStartOfDay(ZoneId.systemDefault())).toInstant());
    }
}
