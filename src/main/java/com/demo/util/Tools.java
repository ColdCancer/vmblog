package com.demo.util;

import java.util.Random;

public class Tools {
//    static public String consequncedIndex(int index) {
//        StringBuilder result = new StringBuilder();
//        while (index != 0) {
//            int num = index % 16;
//            if (num < 10) result.append(num);
//            else result.append((char)num);
//            index /= 16;
//        }
//        return String.valueOf(result.reverse());
//    }

    static public String randomString(int length) {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt() % 16;
            if (num < 10) result.append(num);
            else result.append((char)num);
        }
        return String.valueOf(result.reverse());
    }

    public static boolean buildFile() {

        return true;
    }
}
