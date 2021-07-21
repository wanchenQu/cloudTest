package com.chenTest.springcloud.controller;


import com.chenTest.springcloud.services.OrderHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_global_FallBackMethod")
public class OrderHystrixController {

    @Resource
    private OrderHystrixService orderHystrixService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id) {
        //int a = 1/0;
        String result = orderHystrixService.paymentInfo_OK(id);
        return result;
    }

//    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
//                    value = "1500")
//    })
    @HystrixCommand
    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    public String paymentInfo_Timeout(@PathVariable("id") Integer id) {
        // int a = 1/0;
        String result = orderHystrixService.paymentInfo_Timeout(id);
        return result;
    }

    public String paymentInfo_TimeOutHandler(Integer id) {
        return "我是消费者80：调用支付接口超时或异常/本系统程序异常：\t" + "\t当前线程池名字：" + Thread.currentThread().getName();
    }

    // 全局fallback方法
    public String payment_global_FallBackMethod() {
        return "Global FallBack Method: 异常，请稍后重试";
    }
}
