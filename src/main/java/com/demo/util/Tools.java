package com.demo.util;

import java.util.Random;

public class Tools {

    static public String randomString(int length) {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(Integer.toHexString(random.nextInt(16)));
//            int num = random.nextInt() % 16;
//            if (num < 10) result.append(num);
//            else result.append((char)('A' + num - 10));
        }
        return result.toString().toUpperCase();
    }

    public static boolean buildFile(String account, String id, String content) {
        String url =
        return true;
    }
}
