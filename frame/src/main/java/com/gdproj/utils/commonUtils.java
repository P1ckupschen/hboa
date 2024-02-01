package com.gdproj.utils;

import java.util.Date;

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

    /**
     * 时间间隔
     *
     * */
    public static Long getIntervalDays(Date start , Date end){
        final long ONE_DAY_MILLIS = 1000L * 60 * 60 * 24;
        // 此处要注意，去掉时分秒的差值影响，此处采用先换算为天再相减的方式
        return Math.abs(end.getTime()/ONE_DAY_MILLIS - start.getTime()/ONE_DAY_MILLIS);
    }


}
