package com.demo.utils;

import com.sun.corba.se.spi.orbutil.fsm.FSMImpl;
import org.apache.commons.codec.digest.DigestUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.logging.SimpleFormatter;

public class BaseTools {
    public static String UUID() {
        return UUID.randomUUID().toString();
    }

    public static String randomStr(int length) {
        String example = "0123456789" +
                "abcdefghijklmnopqrstuvwxyz" +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(example.length());
            char charAt = example.charAt(number);
            sb.append(charAt);
        }
        return sb.toString();
    }

    public static Date toDate(String date_str) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return dateFormat.parse(date_str);
        } catch (ParseException e) {
//            e.printStackTrace();
        }
        return null;
    }

    public static String digest(String str1, String str2) {
        return DigestUtils.shaHex(str1 + "&" + str2);
    }

    public static String toString(Date date) {
        if (date == null) return "-";
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
}
