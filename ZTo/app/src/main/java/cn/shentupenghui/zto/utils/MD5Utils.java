
/*
 * Copyright (c) 2018.
 * Created by shentupenghui
 * Last modified 18-4-18 下午1:55
 */

package cn.shentupenghui.zto.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    //    MD5加密算法
    public static String md5(String text) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(text.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : result) {
                int number = b & 0xff;
                String hex = Integer.toHexString(number);
                if (hex.length() == 1) {
                    stringBuilder.append("0" + hex);
                } else {
                    stringBuilder.append(hex);
                }
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

    }
}
