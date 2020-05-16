package com.hx.steven.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ^((13[4-9])|(147)|(15[0-2,7-9])|(178)|(18[2-4,7-8]))\\d{8}|(1705)\\d{7}$
 * ^((13[0-2])|(145)|(15[5-6])|(176)|(18[5,6]))\\d{8}|(1709)\\d{7}$
 * ^((133)|(153)|(177)|(18[0,1,9]))\\d{8}$
 */

public class RegexUtil {

    /**
     * 手机号码，中间4位星号替换
     *
     * @param phone 手机号
     * @return 星号替换的手机号
     */
    public static String phoneNoHide(String phone) {
        // 括号表示组，被替换的部分$n表示第n组的内容
        // 正则表达式中，替换字符串，括号的意思是分组，在replace()方法中，
        // 参数二中可以使用$n(n为数字)来依次引用模式串中用括号定义的字串。
        // "(\d{3})\d{4}(\d{4})", "$1****$2"的这个意思就是用括号，
        // 分为(前3个数字)中间4个数字(最后4个数字)替换为(第一组数值，保持不变$1)(中间为*)(第二组数值，保持不变$2)
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 银行卡号，保留最后4位，其他星号替换
     *
     * @param cardId 卡号
     * @return 星号替换的银行卡号
     */
    public static String bankCardIdNoHide(String cardId) {
        return cardId.replaceAll("\\d{15}(\\d{3})", "**** **** **** **** $1");
    }

    /**
     * 身份证号，中间10位星号替换
     *
     * @param id 身份证号
     * @return 星号替换的身份证号
     */
    public static String idCardNoHide(String id) {
        return id.replaceAll("(\\d{4})\\d{10}(\\d{4})", "$1** **** ****$2");
    }

    /**
     * 判断是否是手机号
     *
     * @param phone
     * @return
     */
    public static boolean isPhoneNo(String phone) {
        Pattern p1 = Pattern
                .compile("^((13[4-9])|(147)|(15[0-2,7-9])|(178)|(18[2-4,7-8])|(199))\\d{8}|(1705)\\d{7}$");
        Matcher m1 = p1.matcher(phone);

        Pattern p2 = Pattern
                .compile("^((13[0-2])|(145)|(15[5-6])|(176)|(18[5,6]))\\d{8}|(1709)\\d{7}$");
        Matcher m2 = p2.matcher(phone);

        Pattern p3 = Pattern
                .compile("^((133)|(153)|(177)|(18[0,1,9]))\\d{8}$");
        Matcher m3 = p3.matcher(phone);
        return m1.matches() || m2.matches() || m3.matches();
    }

    /**
     * 6到16位数字加字母组合
     *
     * @param pwd
     * @return
     */
    public static boolean isPwdValid(String pwd) {
        Pattern p = Pattern
                .compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$");
        Matcher m = p.matcher(pwd);
        return m.matches();
    }

    /**
     * 邮箱
     *
     * @param str
     * @return
     */
    public static boolean email(String str) {
        Pattern p = Pattern.compile("^[A-Za-zd]+([-_.][A-Za-zd]+)*@([A-Za-zd]+[-.])+[A-Za-zd]{2,5}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 验证固定电话号码
     *
     * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
     *              <p><b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字，
     *              数字之后是空格分隔的国家（地区）代码。</p>
     *              <p><b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
     *              对不使用地区或城市代码的国家（地区），则省略该组件。</p>
     *              <p><b>电话号码：</b>这包含从 0 到 9 的一个或多个数字 </p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isHomePhoneNo(String phone) {
        String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
        return Pattern.matches(regex, phone);
    }

    /**
     * 判断一个字符串是否包含中文
     *
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str) {
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
    public static boolean isAllChinese(String str) {
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
    public static boolean isContainNumber(String str) {
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
    public static boolean isContainXing(String str) {
        Pattern p = Pattern.compile(".*\\*.*");
        Matcher m = p.matcher(str);
        if (m.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 校验身份证 的合法性  十八位身份证
     */
    public static boolean isIDCardNo(String str) {
        Pattern p = Pattern.compile("^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$");
        Matcher m = p.matcher(str);
        if (m.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 验证日期（年月日）
     *
     * @param birthday 日期，格式：1992-09-03，或1992.09.03
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkBirthday(String birthday) {
        String regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}";
        return Pattern.matches(regex, birthday);
    }

    /**
     * 验证URL地址
     *
     * @param url 格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或 http://www.csdn.net:80
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkURL(String url) {
        String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
        return Pattern.matches(regex, url);
    }

    /**
     * 匹配中国邮政编码
     *
     * @param postcode 邮政编码
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPostcode(String postcode) {
        String regex = "[1-9]\\d{5}";
        return Pattern.matches(regex, postcode);
    }

    /**
     * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
     *
     * @param ipAddress IPv4标准地址
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIpAddress(String ipAddress) {
        String regex = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";
        return Pattern.matches(regex, ipAddress);
    }

}
