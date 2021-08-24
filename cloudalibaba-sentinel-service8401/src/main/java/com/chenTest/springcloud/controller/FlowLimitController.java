package com.chenTest.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.sun.deploy.security.BlockedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping(value = "/testA")
    public String testA() {
//        try {
//            TimeUnit.MILLISECONDS.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return "------------testA";
    }

    @GetMapping(value = "/testB")
    public String testB() {
        log.info(Thread.currentThread().getName() + "\t" + "------------------testB");
        return "------------testB";
    }

    @GetMapping("/testD")
    public String testD() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("------testD");
        return "------testD";
    }

    @GetMapping(value = "/testHotKey")
    @SentinelResource(value = "testHotKey", blockHandler = "blockMethod")
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2) {
        return "--------------testHotKey";
    }

    public String blockMethod(String p1, String p2, BlockException e) {
        return "--------------blockMethod";
    }
}
