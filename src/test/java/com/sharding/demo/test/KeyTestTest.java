package com.sharding.demo.test;


import com.sharding.demo.dao.KeyTestDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class KeyTestTest {

    @Autowired
    private KeyTestDao keyTestDao;

    @Test
    public void getList() {
        Integer list = keyTestDao.getList();
        System.out.println(list);
    }

    @Test
    public void insert() {
        keyTestDao.insert();
    }
}
