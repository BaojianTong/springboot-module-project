package com.biyouche.utils;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public class MD5 {

    public static final int BUFFERSIZE = 8196;
    public static final String ALGORITHM = "MD5";
    public static final String CHARSET = "UTF-8";

    /**
     * 签名字符串（UTF-8字符集）
     *
     * @param input 需要签名的字符串
     * @return 验证结果
     */
    public static final String md5(String input) {
        return md5(input, CHARSET);
    }

    /**
     * 签名字符串
     *
     * @param input       需要签名的字符串
     * @param charsetName 编码格式
     * @return 签名结果
     */
    public static final String md5(String input, String charsetName) {
        try {
            byte md5Bytes[] = md5(getContentBytes(input, charsetName));
            return StringUtils.byte2hex(md5Bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return input;
    }

    /**
     * 签名字符串
     *
     * @param srcBytes 需要签名的byte数组
     * @return 签名结果
     */
    public static final byte[] md5(byte[] srcBytes) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(ALGORITHM).digest(srcBytes);
    }

    /**
     * 签名字符串
     *
     * @param text          需要签名的字符串
     * @param key           密钥
     * @param charset 编码格式
     * @return 签名结果
     */
    public static final String md5(String text, String key, String charset) throws Exception {
        return md5(text + key, charset);
    }

    public static final String md5file(String filename) {
        BufferedInputStream bufferedInputStream = null;
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(filename), BUFFERSIZE);
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            byte[] buffer = new byte[BUFFERSIZE];
            int i = 0;
            while ((i = bufferedInputStream.read(buffer)) != -1) {
                md.update(buffer, 0, i);
            }
            return StringUtils.byte2hex(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                bufferedInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 签名字符串
     *
     * @param text          需要签名的字符串
     * @param sign          签名结果
     * @param key           密钥
     * @param charset 编码格式
     * @return 验证结果
     */
    public static final boolean verify(String text, String sign, String key, String charset) throws Exception {
        if (sign.equals(md5(text, key, charset))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param content 内容
     * @param charset 字符集
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException
     */
    private static final byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    public static void main(String[] args) {
        String pwd = MD5.md5("123456" + "@byc2018");
        System.out.println(pwd);
    }

}
