package com.gdproj.utils;

public class commonUtils {

    public static void main(String[] args) {
        String phoneNumber = "13812345678";
        String desensitizedPhoneNumber = desensitizePhoneNumber(phoneNumber);
        System.out.println(desensitizedPhoneNumber); // 输出：138****5678
    }
    /**
     * 手机号脱敏
     * */
    public static String desensitizePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return "";
        }

        String prefix = phoneNumber.substring(0, 3);
        String suffix = phoneNumber.substring(phoneNumber.length() - 4);

        StringBuilder asterisks = new StringBuilder();
        for (int i = 3; i < phoneNumber.length() - 4; i++) {
            asterisks.append("*");
        }
        return prefix + asterisks.toString() + suffix;
    }
}
