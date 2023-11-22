package com.gdproj.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;
 
 
public class OLDAESUtil {
    // 128位密钥 = 16 bytes Key:
    public static String key128 = "1234567890abcdef";
 
 
    public static void main(String[] args) throws GeneralSecurityException {
        String message = "你好123";
        byte[] data = message.getBytes(StandardCharsets.UTF_8);
 
        //ECB工作模式
        // 加密:
        byte[] encrypted = encryptECB(key128, data);
        System.out.println("Encrypted: " + Base64.getEncoder().encodeToString(encrypted));
        // 解密:
        byte[] decrypted = decryptECB(key128, Base64.getDecoder().decode("OTWaKczc9APL05Qp9Fz2jA=="));
        System.out.println("Decrypted: " + new String(decrypted, StandardCharsets.UTF_8));
 
        //CBC工作模式
        // 加密:
        byte[] encrypted2 = encryptCBC(key128,data);
        System.out.println("Encrypted: " + Base64.getEncoder().encodeToString(encrypted2));
        // 解密:
        byte[] decrypted2 = decryptCBC(key128, encrypted2);
        System.out.println("Decrypted: " + new String(decrypted2, StandardCharsets.UTF_8));
 
        //String类型的ECB工作模式
        System.out.println(encryptECB(key128, "你好123"));
        System.out.println(decryptECB(key128, "7YqFnvEnVhmw4LEYHV5prvHtLs2joAtfRki0nSoHTUSGZCniiI7CEgBN8bnFkNGtAKJV93TQBGRz062kNKhsopRbd3bhESAV/dVLuq74Ohz6OLMntbCFyoox1yihVxH2PxVwtnjdjVfAO7FMSVMElj9QS70zwgVDUd9/ILse6jtguWV2NLdK/W7eeKAgA5BLQ+cmPikhkeyQxDxQG4ctPA=="));
 
 
    }
 
    // String类型加密:
    public static String encryptECB(String key, String input) throws GeneralSecurityException {
        byte[] bytes = OLDAESUtil.encryptECB(key, input.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(bytes);
    }
 
    // 加密:
    public static byte[] encryptECB(String key, byte[] input) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        return cipher.doFinal(input);
    }
 
    // 解密:
    public static String decryptECB(String key, String input) throws GeneralSecurityException {
        byte[] bytes = OLDAESUtil.decryptECB(key128, Base64.getDecoder().decode(input));
        return new String( bytes, StandardCharsets.UTF_8);
    }
    // 解密:
    public static byte[] decryptECB(String key, byte[] input) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        return cipher.doFinal(input);
    }
 
 
    // 加密:
    public static byte[] encryptCBC(String key, byte[] input) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        // CBC模式需要生成一个16 bytes的initialization vector:
        SecureRandom sr = SecureRandom.getInstanceStrong();
        byte[] iv = sr.generateSeed(16);
        IvParameterSpec ivps = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivps);
        byte[] data = cipher.doFinal(input);
        // IV不需要保密，把IV和密文一起返回:
        return join(iv, data);
    }
 
    // 解密:
    public static byte[] decryptCBC(String key, byte[] input) throws GeneralSecurityException {
        // 把input分割成IV和密文:
        byte[] iv = new byte[16];
        byte[] data = new byte[input.length - 16];
        System.arraycopy(input, 0, iv, 0, 16);
        System.arraycopy(input, 16, data, 0, data.length);
        // 解密:
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        IvParameterSpec ivps = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivps);
        return cipher.doFinal(data);
    }
 
    public static byte[] join(byte[] bs1, byte[] bs2) {
        byte[] r = new byte[bs1.length + bs2.length];
        System.arraycopy(bs1, 0, r, 0, bs1.length);
        System.arraycopy(bs2, 0, r, bs1.length, bs2.length);
        return r;
    }
 
}