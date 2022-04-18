package com.demo.utils;

import com.sun.corba.se.spi.orbutil.fsm.FSMImpl;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseTools {
    private static final String web_name = "/WEB-INF/";
    private static final String blogger_name = web_name + "blogger/";
    private static final String mediae_name = web_name + "mediae/";
    private static final long SECOND = 1000;
    private static final long MINUTES = 60 * SECOND;
    private static final long HOUR = 60 * MINUTES;
    private static final long DAY = 24 * HOUR;
    private static final long MONTH = 30 * DAY;
    private static final long YEAR = 12 * MONTH;

    public static String getMDPath(HttpSession session, String account, String file_name) {
        String base_path = session.getServletContext().getRealPath(blogger_name);
        String file_path = base_path + account + File.separator + "article" + File.separator;
        File dir = new File(file_path);
        if (!dir.exists()) {
            boolean flag = dir.mkdirs();
        }
        return file_path + file_name;
    }

    public static boolean deleteMarkdown(String file_path) {
        File sfile = new File(file_path + ".save");
        if (sfile.exists()) { boolean flag = sfile.delete(); }
        File file = new File(file_path + ",md");
        if (file.exists()) { boolean flag = file.delete(); }
        return true;
    }

    public static String subDate(Date start, Date end) {
        long sub = end.getTime() - start.getTime();
        long year = sub / YEAR;
        long month = sub % YEAR / MONTH;
        long day = sub % MONTH / DAY;
        long hour = sub % DAY / HOUR;
        long minutes = sub % HOUR / MINUTES;
        long second = sub % MINUTES / SECOND;
        String result = "";
        if (year != 0) result += Long.toString(year) + " year ";
        if (month != 0) result += Long.toString(month) + " mon ";
        if (day != 0) result += Long.toString(day) + " day ";
        if (hour != 0) result += Long.toString(hour) + " hour ";
        if (minutes != 0) result += Long.toString(minutes) + " min ";
        if (second != 0) result += Long.toString(second) + " s";
        return result;
    }

    public static String getResourcesPath(HttpSession session, String account, String file_name) {
        String base_path = session.getServletContext().getRealPath(blogger_name);
        if ("../".equals(file_name)) return base_path + account + File.separator;

        String file_path = base_path + account + File.separator + "resources" + File.separator;
        File dir = new File(file_path);
        if (!dir.exists()) {
            boolean flag = dir.mkdirs();
        }
        return file_path + file_name;
    }

    public static String getImagePath(HttpSession session, String file_name) {
        String base_path = session.getServletContext().getRealPath(mediae_name);
        String file_path = base_path + "images" + File.separator;
        File dir = new File(file_path);
        if (!dir.exists()) {
            boolean flag = dir.mkdirs();
        }
        return file_path + file_name;
    }

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

    public static String delHTMLTag(String htmlStr){
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式
        String regEx_img = "!\\[[\\s\\S]*?\\]\\([\\S]*?\\)";

        Pattern p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); //过滤script标签

        Pattern p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); //过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); //过滤html标签

        Pattern p_img = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        Matcher m_img = p_img.matcher(htmlStr);
        htmlStr = m_img.replaceAll("");

        return htmlStr.trim(); //返回文本字符串
    }

    public static void fileCopy(String srcPath, String destPath){
        File f = new File(srcPath);
        File F = new File(destPath);

        byte [] a = new byte[1024];
        try {
            FileInputStream f1 = new FileInputStream(f);
            FileOutputStream F1 = new FileOutputStream(F);

            while(true) {
                int b = f1.read(a);

                if (b == -1) break;
                F1.write(a, 0, b);
                F1.flush();
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void folderCopy(String srcPath, String destPath){
        File srcFolder = new File(srcPath);
        File destFolder = new File(destPath);
        if(!srcFolder.isDirectory()) return;
        if (!srcFolder.exists()) return;
        if (destFolder.isFile()) return;

        if(!destFolder.exists()) {
            boolean flag = destFolder.mkdirs();
        }

        File [] files=srcFolder.listFiles();
        for(File srcFile : files != null ? files : new File[0]) {
            if (srcFile.isFile()) {
                File newDestFile = new File(destFolder, srcFile.getName());
                fileCopy(srcFile.getAbsolutePath(), newDestFile.getAbsolutePath());
            }

            if(srcFile.isDirectory()) {
                File newDestFolder = new File(destFolder,srcFile.getName());
                folderCopy(srcFile.getAbsolutePath(),newDestFolder.getAbsolutePath());
            }
        }
    }


}
