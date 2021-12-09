package com.sharding.demo.test;


import com.sharding.demo.dao.KeyTestDao;
import com.sharding.demo.service.KeyTestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class KeyTestTest {

    @Autowired
    private KeyTestDao keyTestDao;

    @Autowired
    private KeyTestService keyTestService;

    @Test
    public void getList() {
        Integer list = keyTestDao.getList();
        System.out.println(list);
    }

    @Test
    public void insert() {
        keyTestDao.insert();
    }

    @Test
    public void InterAfterGet() {
        keyTestService.InterAfterGet();
    }

    @Test
    public void getAfterInsert() {
        keyTestService.getAfterInsert();
    }
}
