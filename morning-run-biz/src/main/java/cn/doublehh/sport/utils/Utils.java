package cn.doublehh.sport.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author 胡昊
 * Description:工具类
 * Date: 2018/12/1
 * Time: 16:21
 * Create: DoubleH
 */
public class Utils {
    private final static DateTimeFormatter dfForDevice = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private final static DateTimeFormatter dfForNormal = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 转换考勤机时间到本地格式时间
     *
     * @param time 考勤机时间
     * @return
     */
    public static String transferDateTimeForDevice(String time) {
        return dfForNormal.format(LocalDateTime.parse(time, dfForDevice));
    }
}
