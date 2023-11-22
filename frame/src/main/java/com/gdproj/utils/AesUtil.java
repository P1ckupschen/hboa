package com.gdproj.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * AES工具类
 * @author weizhenhui
 * @date 2021/4/30 14:37
 */
public class AesUtil {
    private static final String CHARSET_NAME = "UTF-8";
    private static final String KEY_ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM = "AES/CBC/NoPadding";

    public static final byte[] key128 = "1234567890abcdef".getBytes(StandardCharsets.UTF_8);
    private AesUtil() {
    }

    /**
     * 生成加密key
     */
    public static byte[] getEncryptKey() {
        KeyGenerator keyGenerator;
        // 实例化一个用AES加密算法的密钥生成器
        try {
            keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("NoSuchAlgorithmException=>" + e.getMessage());
        }
        // bitSize
        keyGenerator.init(128);
        // 生成一个密钥。
        SecretKey key = keyGenerator.generateKey();
        return key.getEncoded();
    }

    /**
     * aes加密-128位
     *
     * @param content  待加密内容
     * @param keyBytes 加密密钥
     * @return 16进制加密数据
     */
    public static String encrypt(String content, byte[] keyBytes) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = content.getBytes(CHARSET_NAME);
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keyspec = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
            IvParameterSpec ivspec = new IvParameterSpec(keyBytes);
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new UnsupportedOperationException("AES加密异常", e);
        }
    }

    /**
     * 校验密钥
     *
     * @param key 32位长度的数字字符串密钥
     */
    private static byte[] getKeyBytes(String key) {
        if (key == null || key.length() == 0) {
            throw new IllegalArgumentException("密钥不能为空");
        }
        byte[] keyBytes = hex2Bytes(key);
        if (keyBytes.length != 16) {
            throw new IllegalArgumentException("密钥长度为16位");
        }
        return keyBytes;
    }

    /**
     * aes加密-128位
     *
     * @param content 待加密内容
     * @param key     加密密钥
     * @return 16进制加密数据
     */
    public static String encryptByKey(String content, String key) {
        byte[] keyBytes = getKeyBytes(key);
        return encrypt(content, keyBytes);
    }

    /**
     * aes解密-128位
     *
     * @param encryptContent 待解密内容
     * @param key            解密密钥
     */
    public static String decryptByKey(String encryptContent, String key) {
        byte[] keyBytes = getKeyBytes(key);
        return decrypt(encryptContent, keyBytes);
    }

    /**
     * aes解密-128位
     *
     * @param encryptContent 待解密内容
     * @param keyBytes       解密密钥
     */
    public static String decrypt(String encryptContent, byte[] keyBytes) {
        try {
            byte[] encrypted1 = Base64.getDecoder().decode(encryptContent);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            SecretKeySpec keyspec = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
            IvParameterSpec ivspec = new IvParameterSpec(keyBytes);
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted1);
            String value = new String(original, CHARSET_NAME).trim();
            return (value == null || value.trim().length() == 0) ? encryptContent : value;
        } catch (Exception e) {
            throw new UnsupportedOperationException("AES解密异常", e);
        }
    }

    /**
     * 将byte[] 转换成字符串
     */
    public static String byte2Hex(byte[] sourceBytes) {
        StringBuilder hexBuilder = new StringBuilder();
        for (byte b : sourceBytes) {
            String hexString = Integer.toHexString(0x00ff & b);
            hexBuilder.append(hexString.length() == 1 ? 0 : "").append(hexString);
        }
        return hexBuilder.toString();
    }

    /**
     * 将16进制字符串转为转换成字符串
     */
    public static byte[] hex2Bytes(String source) {
        byte[] sourceBytes = new byte[source.length() / 2];
        for (int i = 0; i < sourceBytes.length; i++) {
            sourceBytes[i] = (byte) Integer.parseInt(source.substring(i * 2, i * 2 + 2), 16);
        }
        return sourceBytes;
    }

}
