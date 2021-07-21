package com.chenTest.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;


public interface LoadBalancer {

    // 收集活跃机器总数
    ServiceInstance instance(List<ServiceInstance> instances);
}
