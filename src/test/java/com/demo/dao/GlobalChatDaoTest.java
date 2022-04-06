package com.demo.dao;

import com.demo.entity.GlobalChat;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:applicationContext.xml",
        "classpath:mybatis-config.xml",
        "classpath:springmvc-config.xml"})
@WebAppConfiguration("src/test/resources")
public class GlobalChatDaoTest extends TestCase {
    @Resource
    private GlobalChatDao GlobalChatDao;

    @Test
    public void testQueryById() {
        GlobalChat aChat = GlobalChatDao.queryById(1);
        System.out.println(aChat);
    }

    @Test
    public void testQueryAllByLimit() {
        List<GlobalChat> GlobalChatList = GlobalChatDao.queryAllByLimit(1, 0);
        for (GlobalChat aChat : GlobalChatList) {
            System.out.println(aChat);
        }
    }

    @Test
    public void insert() {
        GlobalChat aChat = new GlobalChat(null, 1, null, "hello ssm");
        if (GlobalChatDao.insert(aChat) > 0) {
            System.out.println("insert success");
        } else {
            System.out.println("insert failure");
        }
    }


    @Test
    public void deleteById() {
        if (GlobalChatDao.deleteById(3) > 0) {
            System.out.println("delete success");
        } else {
            System.out.println("delete failure");
        }
    }
}