package club.xiaozhe.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class DataCheck {
    private DataCheck() {
    }

    // 1. 日期：YYYY-MM-DD  （0001-9999年，考虑闰年）
    public static final String REGEX_DATE =
            "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";

    // 2. 中国大陆手机号，11 位，1 开头，第 2 位 3-9
    public static final String REGEX_PHONE = "^1[3-9]\\d{9}$";

    // 3. 邮箱：常规 RFC 简化版
    public static final String REGEX_EMAIL =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    // 4. 学号：符合现实特色，11位数字
    public static final String REGEX_ID = "\\d{11}";

    private static final Pattern DATE_PATTERN = Pattern.compile(REGEX_DATE);
    private static final Pattern PHONE_PATTERN = Pattern.compile(REGEX_PHONE);
    private static final Pattern EMAIL_PATTERN = Pattern.compile(REGEX_EMAIL);

    private static final Pattern ID_PATTERN = Pattern.compile(REGEX_ID);

    /**
     * 判断字符串是否为日期（YYYY-MM-DD）
     *
     * @param date 日期字符串
     * @return 符合格式返回true，否则返回false
     */
    public static boolean isValidDate(String date) {
        if (date == null) return false;
        if (!DATE_PATTERN.matcher(date).matches())
            return false;

        try {
            LocalDate.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否为电话
     *
     * @param telephone 电话字符串
     * @return 符合格式返回true，否则返回false
     */
    public static boolean isValidTelephone(String telephone) {
        if (telephone == null) return false;
        return PHONE_PATTERN.matcher(telephone).matches();
    }

    /**
     * 判断字符串是否为邮箱
     *
     * @param email 邮箱字符串
     * @return 符合格式返回true，否则返回false
     */
    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * 判断字符串是否为性别合法值
     *
     * @param sex 性别字符串
     * @return true（男、女、保密），否则为false
     */
    public static boolean isValidSex(String sex) {
        return "男".equals(sex) || "女".equals(sex) || "保密".equals(sex);
    }

    /**
     * 判断字符串是否为学号
     *
     * @param studentId 学号字符串
     * @return 符合格式返回true，否则返回false
     */
    public static boolean isValidId(String studentId) {
        if (studentId == null) return false;
        return ID_PATTERN.matcher(studentId).matches();
    }

    /**
     * 判断字符串是否是数字串
     *
     * @param str 字符串
     * @return 如果是数字串返回true，否则返回false
     */
    public static boolean isNumString(String str) {
        return Pattern.compile("-?(0|[1-9]\\d*)").matcher(str).matches();
    }
}