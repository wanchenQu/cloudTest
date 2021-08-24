package com.chenTest.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.chenTest.springcloud.entities.CommonResult;
import com.chenTest.springcloud.entities.Payment;
import com.chenTest.springcloud.service.PaymentService;
import com.sun.deploy.security.BlockedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class CircuitBreakerController {

    @Value("${server-url.nacos-user-service}")
    private String SERVER_URL;

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private PaymentService paymentService;

    @RequestMapping("/consumer/fallback/{id}")
    @SentinelResource(value = "fallback", fallback = "handlerFallback",
            blockHandler = "blockHandler")
    public CommonResult<Payment> fallback(@PathVariable("id") Long id) {
        CommonResult<Payment> result = restTemplate.getForObject(SERVER_URL + "/paymentSQL/" + id, CommonResult.class, id);
        if (id == 4) {
            throw new IllegalArgumentException("IllegalArgumentException, 非法参数异常!");
        } else if (result.getData() == null) {
            throw new NullPointerException("NullPointerException, ID无记录，空指针异常");
        }
        return result;
    }

    @GetMapping(value = "/consumer/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id) {
        return paymentService.paymentSQL(id);
    }


    /**
     * handlerFallback: 兜底处理异常方法
     */
    public CommonResult handlerFallback(Long id, Throwable e) {
        Payment payment = new Payment(id, "null");
        return new CommonResult<>(444, "兜底异常handlerFallback，exception内容 " + e.getMessage(), payment);
    }

    /**
     * blockHandler: 负责处理sentinel限流分支
     */
    public CommonResult blockHandler(Long id, BlockException e) {
        Payment payment = new Payment(id, "null");
        return new CommonResult<>(445, "blockHandler-sentinel限流，无此流水：blockException " + e.getMessage());
    }
}
