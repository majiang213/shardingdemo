package com.sharding.demo.service;

import com.sharding.demo.dao.KeyTestDao;
// import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class KeyTestService {

    @Autowired
    private KeyTestDao keyTestDao;

    public Integer getList() {
        return keyTestDao.getList();
    }

    public void InterAfterGet() {
        keyTestDao.insert();
        System.out.println(keyTestDao.getList());
    }

    public void getAfterInsert() {
        /*try (HintManager instance = HintManager.getInstance()) {
            instance.setMasterRouteOnly();
            System.out.println(keyTestDao.getList());
            keyTestDao.insert();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
    }
}
