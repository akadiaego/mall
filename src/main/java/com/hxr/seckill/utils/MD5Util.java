package com.hxr.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;



/**
 * MD5工具类
 * @author X.R.Huang
 * @since 1.0.0
 */
@Component
public class MD5Util {
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }
    private static final String salt = "1a2b3c4d";

    public static String inputPassToFromPass(String inputPass){
        String str = ""+salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(3);
        return md5(str);
    }
    public static String formPassToDBPass(String formPass,String salt){
        String str = "" + salt.charAt(0)+salt.charAt(2)+formPass+salt.charAt(5)+salt.charAt(3);
        return md5(str);
    }
    public static String inputPassToDBPass(String inputPass,String salt){
        String formPass = inputPassToFromPass(inputPass);
        String dbPass = formPassToDBPass(formPass, salt);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFromPass("123456"));
        System.out.println(formPassToDBPass("fd829236646594e815c06ab8cc69cc69","1a2b3c4d"));
        System.out.println(inputPassToDBPass("123456","1a2b3c4d"));
    }
}
