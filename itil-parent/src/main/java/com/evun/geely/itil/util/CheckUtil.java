package com.evun.geely.itil.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @version 1.0
 * @Desription:请求校验
 * @Author:Hui
 * @CreateDate:2018/4/5 16:05
 */
public class CheckUtil {

    public static  boolean checkSignature(String signature,String timestamp,String nonce,String token){
        String[] strs = new String[]{token,timestamp,nonce};
        //排序
        Arrays.sort(strs);
        //拼接字符串
        StringBuffer stringBuffer = new StringBuffer();
        for (String str:strs) {
            stringBuffer.append(str);
        }
        String temp = sha1Encode(stringBuffer.toString());
        return null == temp ? false : temp.equals(signature);
    }

    private static String sha1Encode(String str){
        if(null == str){
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }
}
