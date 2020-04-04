package org.yu.myorm.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class commonUtil {

    private final static String UNDER_LINE_REGEX = "_([a-z])";

    /**
     * 下划线转驼峰 stu_name_for -> stuNameFor
     * 
     * @param str
     * @return
     */
    public static String lineToHump(String str) {
        StringBuffer sb = new StringBuffer();
        Matcher matcher = Pattern.compile(UNDER_LINE_REGEX).matcher(str);

        while (matcher.find()) {
            /**
             * String fieledName = columnName.replace("_([a-z])", "\\U\\1\\E");    // mytest in https://regex101.com/r/Wt7Phq/1 
             * 但 Java 的正则匹配不能使用 \Q\1\E 分组转大写，所以使用以下代码
             * 参考「dr.liuzg」https://blog.csdn.net/weixin_42958809/java/article/details/89135467
             * Key Code as follow
             */
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}