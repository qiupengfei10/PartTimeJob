package com.zhitou.job.parttimejob.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qiupengfei on 2017/10/17.
 *
 * 验证相关的方法
 */
public class VerifyUtil {
    /**
     * 验证是否为手机号
     * @param phone 待验证的字符串
     * @return false 表示非手机号 true 表示为手机号
     */
    public static boolean isPhoneNum(String phone) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(phone);
        b = m.matches();
        return b;
    }
}
