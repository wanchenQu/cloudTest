package com.chenTest.springcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class FlowLimitController {

    @GetMapping(value = "/testA")
    public String testA() {
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "------------testA";
    }

    @GetMapping(value = "/testB")
    public String testB() {
        return "------------testB";
    }
}
