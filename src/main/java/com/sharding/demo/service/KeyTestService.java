package com.sharding.demo.service;

import com.sharding.demo.dao.KeyTestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyTestService {

    @Autowired
    private KeyTestDao keyTestDao;

    public Integer getList() {
        return keyTestDao.getList();
    }
}
