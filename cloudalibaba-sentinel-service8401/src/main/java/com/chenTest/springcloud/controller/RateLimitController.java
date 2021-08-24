package com.chenTest.springcloud.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.chenTest.springcloud.entities.CommonResult;
import com.chenTest.springcloud.entities.Payment;
import com.chenTest.springcloud.handler.CustomerBlockHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RateLimitController {

    @GetMapping(value = "/byResource")
    @SentinelResource(value = "byResource1", blockHandler = "handlerException")
    public CommonResult byResource() {
        return new CommonResult(200, "按资源名称限流测试OK", new Payment(2020L, "seiral1001"));
    }

    @GetMapping("/rateLimit/byUrl")
    @SentinelResource(value = "byUrl")
    public CommonResult byUrl() {
        return new CommonResult(200, "按照URL限流测试OK", new Payment(2020L, "serial002"));
    }

    @GetMapping("/reteLimit/CustomerBlockHandler")
    @SentinelResource(value = "CustomerBlockHandler", blockHandlerClass = CustomerBlockHandler.class, blockHandler = "handlerException1")
    public CommonResult CustomerBlockHandler() {
        return new CommonResult(200, "全局的客户自定义处理", new Payment(2020L, "serial003"));
    }

    public CommonResult handlerException(BlockException e) {
        return new CommonResult(404, e.getClass().getCanonicalName() + "\t 服务不可用");
    }
}
