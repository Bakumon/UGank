package me.bakumon.gank.utills;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mafei on 2016/12/8 17:18.
 *
 * @author mafei
 * @version 1.0.0
 * @class DateUtil
 * @describe 日期工具类
 */
public class DateUtil {
    public static String dateFormat(String timestamp) {
        if (timestamp == null) {
            return "unknown";
        }
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date date = inputFormat.parse(timestamp);
            return outputFormat.format(date);
        } catch (ParseException e) {
            return "unknown";
        }
    }
}

