package com.chenTest.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    public String paymentInfo_OK(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + "\t paymentInfo_OK, id: " + id;
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
                    value = "5000")
    })
    public String paymentInfo_Timeout(Integer id) {
        //int a = 1/0;
        int time_seconds = 3000;
        try {
            TimeUnit.MILLISECONDS.sleep(time_seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + "\t paymentInfo_Timeout, id: "
                + id + "耗时" + time_seconds + "ms";
    }

    public String paymentInfo_TimeOutHandler(Integer id) {
        return "调用支付接口超时或异常：\t" + "\t当前线程池名字：" + Thread.currentThread().getName();
    }


    // ==================服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            // 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            // 请求总数阈值：当在配置时间窗口内达到此数量的失败后，进行短路。10个/10s 注意分母是10s。 默认20个/10s
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            // 时间窗口期：短路多久以后开始尝试是否恢复，默认5s
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            // 失败率达到多少后跳闸
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if (id < 0) {
            throw new RuntimeException("******id 不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();

        return Thread.currentThread().getName() + "\t" + "调用成功，流水号： " + serialNumber;
    }

    //服务降级方法
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
        return "id 不能为负数，请稍后再试， o(╥﹏╥)o  id: " + id;
    }

}
