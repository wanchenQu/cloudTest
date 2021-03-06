package com.chenTest.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
/**
 * 能够自动的获悉刷新的内容，让运维工程师发送POST请求刷新3355。
 * 我们还需要让运维人员发送一下POST请求，刷新一下3355
 * curl -X POST "http://localhost:3355/actuator/refresh"
 * */
public class ConfigClientController {

    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/configInfo")
    public String getConfigInfo() {
        return configInfo;
    }
}
