package com.yxs.subject.infra.utils;

import com.alibaba.druid.filter.config.ConfigTools;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import static java.awt.SystemColor.text;

/**
 * 数据库加密util
 */
public class DruidEncryptUtil {

    //先创建公钥和私钥变量
    private static String publicKey;
    private static String privateKey;

    //加载静态代码块生成密钥
    static {
        try {
            String[] keyPair = ConfigTools.genKeyPair(512);
            publicKey = keyPair[0];
            System.out.println("publicKey:" + publicKey);
            privateKey = keyPair[1];
            System.out.println("privateKey:" + privateKey);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }

    //加密代码，将数据放进去输出加密
    private static String encrypt(String text) {
        try {
            String encrypt = ConfigTools.encrypt(publicKey, text);
            System.out.println("encrypt:" + encrypt);
            return encrypt;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static String decrypt(String encryptText){
        try {
            String decrypt = ConfigTools.decrypt(privateKey, encryptText);
            System.out.println("decrypt:" + decrypt);
            return decrypt;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public static void main(String[] args) throws Exception {




        String encrypt = encrypt("123456");
        System.out.println("encrypt:" + encrypt);
    }
}
