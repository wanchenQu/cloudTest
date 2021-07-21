package com.chenTest.springcloud.controller;

import com.chenTest.springcloud.entities.CommonResult;
import com.chenTest.springcloud.entities.Payment;
import com.chenTest.springcloud.services.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderFeignController {

    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping("/consumer/payment/getFeign/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") long id) {
        return paymentFeignService.getPaymentById(id);
    }

    @GetMapping(value = "/consumer/payment/feign/timeout")
    public String paymentFeignTimeout() {
        // openfeign底层继承ribbon 客户端一般默认等待1s
        return paymentFeignService.paymentFeignTimeout();
    }
}
