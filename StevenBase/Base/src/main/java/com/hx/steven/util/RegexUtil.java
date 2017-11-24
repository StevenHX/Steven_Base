package com.hx.steven.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *   ^((13[4-9])|(147)|(15[0-2,7-9])|(178)|(18[2-4,7-8]))\\d{8}|(1705)\\d{7}$
 *   ^((13[0-2])|(145)|(15[5-6])|(176)|(18[5,6]))\\d{8}|(1709)\\d{7}$
 *   ^((133)|(153)|(177)|(18[0,1,9]))\\d{8}$
 */

public class RegexUtil {
    public static boolean Phone(String phone) {
        Pattern p1 = Pattern
                .compile("^((13[4-9])|(147)|(15[0-2,7-9])|(178)|(18[2-4,7-8]))\\d{8}|(1705)\\d{7}$");
        Matcher m1 = p1.matcher(phone);

        Pattern p2 = Pattern
                .compile("^((13[0-2])|(145)|(15[5-6])|(176)|(18[5,6]))\\d{8}|(1709)\\d{7}$");
        Matcher m2 = p2.matcher(phone);

        Pattern p3 = Pattern
                .compile("^((133)|(153)|(177)|(18[0,1,9]))\\d{8}$");
        Matcher m3 = p3.matcher(phone);
        if (m1.matches() || m2.matches() || m3.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 6到16位数字加字母组合
     * @param pwd
     * @return
     */
    public static boolean LoginPwd(String pwd) {
        Pattern p = Pattern
                .compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$");
        Matcher m = p.matcher(pwd);
        return m.matches();
    }

    /**
     * 邮箱
     * @param str
     * @return
     */
    public static boolean email(String str) {
        Pattern p = Pattern.compile("^[A-Za-zd]+([-_.][A-Za-zd]+)*@([A-Za-zd]+[-.])+[A-Za-zd]{2,5}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 固定电话
     * @param str
     * @return
     */
    public static boolean TelPhone(String str) {
        Pattern p = Pattern.compile("^\\(?0\\d{2,4}\\)?-?\\d{7,8}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 判断一个字符串是否包含中文
     *
     * @param str
     * @return
     */
    public static boolean ContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
    /**
     * 判断一个字符串是否全是中文
     */
    public static boolean AllChinese(String str){
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]+");
        Matcher m = p.matcher(str);
        if (m.matches()) {
            return true;
        }
         return false;
    }

    /**
     * 判断一个字符串是否包含数字
     *
     * @param str
     * @return
     */
    public static boolean ContainNumber(String str) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(str);
        if (m.matches())
            flag = true;
        return flag;
    }

    /**
     * 判断一个字符串是否包含 *
     */
    public static boolean ContainXing(String str){
        Pattern p = Pattern.compile(".*\\*.*");
        Matcher m = p.matcher(str);
        if (m.matches()){
            return true;
        }
        return false;
    }
    /**
     * 校验身份证 的合法性  十八位身份证
     */
    public  static  boolean IDCard(String str){
        Pattern p = Pattern.compile("^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$");
        Matcher m = p.matcher(str);
        if (m.matches()) {
            return true;
        }
        return false;
    }

}
