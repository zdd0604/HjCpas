package com.hj.casps.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/6/4.
 */

public class CheckFormUtils {
    static boolean flag = false;
    static String regex = "";
    private static boolean check(String str, String regex) {
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
            return flag;
    }

    /**
     * 验证非空
     * @param notEmputy
     * @return
     */
    public static boolean checkNotEmputy(String notEmputy) {
        regex = "^\\s*$";
        return check(notEmputy, regex) ? false : true;
    }

    /**
     * 只能输入数字
     * @param str
     * @return
     */

    public final static boolean isDigits(String str) {
        return check(str, "^[0-9]*$");
    }


    /**
     * 联系电话(手机/电话皆可)验证
     *
     * @param text
     * @return
     * @author jiqinlin
     */
    public final static boolean isTel(String text){
        if(isMobile(text)||isPhone(text)) return true;
        return false;
    }
    /**
     * 电话号码验证
     *
     * @param text
     * @return
     * @author jiqinlin
     */
    public final static boolean isPhone(String text){
        return check(text, "^(\\d{3,4}-?)?\\d{7,9}$");
    }
    /**
     * 手机号码验证
     *
     * @param text
     * @return
     * @author jiqinlin
     */
    public final static boolean isMobile(String text){
        if(text.length()!=11) return false;
        return check(text, "^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\\d{8})$");
    }



    /**
     * 判断英文字符(a-zA-Z)
     *
     * @param text
     * @return
     * @author jiqinlin
     */
    public final static boolean isEnglish(String text){
        return check(text, "^[A-Za-z]+$");
    }


    /**
     * 匹配汉字
     *
     * @param text
     * @return
     * @author jiqinlin
     */
    public final static boolean isChinese(String text){
        return check(text, "^[\u4e00-\u9fa5]+$");
    }
    /**
     * 验证字符，只能包含中文、英文、数字、下划线等字符。
     *
     * @param str
     * @return
     * @author jiqinlin
     */
    public final static boolean stringCheck(String str) {
        return check(str, "^[a-zA-Z0-9\u4e00-\u9fa5-_]+$");
    }


}
