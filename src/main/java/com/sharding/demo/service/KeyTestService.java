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
        return keyTestDao.getCount();
    }

    public void InterAfterGet() {
        keyTestDao.insert();
        keyTestDao.getCount();
    }

    public void getAfterInsert() {
        // hintManager实现了AutoCloseable接口
        /*try (HintManager instance = HintManager.getInstance()) {
            // 强制使用主库，如果spring.sharding.enabled为false。该代码会自动失效，不会抛出异常
            instance.setMasterRouteOnly();
            keyTestDao.getCount();
            keyTestDao.insert();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
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
