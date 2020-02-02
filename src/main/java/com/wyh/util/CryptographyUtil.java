package com.wyh.util;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 加密工具
 * @author wyh
 *
 */
public class CryptographyUtil {

    public final static String SALT="wyh"; // 加密的盐

    /**
     * Md5加密
     * @param str
     * @param salt
     * @return
     */
    public static String md5(String str,String salt){
        return new Md5Hash(str,salt).toString();
    }

    public static void main(String[] args) {
        String password="123456";

        System.out.println("Md5加密："+CryptographyUtil.md5(password, SALT));
    }
}
