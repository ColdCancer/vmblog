package com.demo.util;

import java.io.FileWriter;
import java.io.IOException;
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
        String path = Tools.class.getResource("/").getPath();
        path = path.substring(1, path.length() - 8);
        path = path + "article/" + account + "/" + id + ".md";

        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(content);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }
}
