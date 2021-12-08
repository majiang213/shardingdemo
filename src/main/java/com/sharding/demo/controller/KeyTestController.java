package com.sharding.demo.controller;

import com.sharding.demo.service.KeyTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/keyTest")
public class KeyTestController {

    @Autowired
    private KeyTestService keyTestService;

    @GetMapping("getList")
    public void getList() {
        Integer list = keyTestService.getList();
        System.out.println(list);
    }
}
