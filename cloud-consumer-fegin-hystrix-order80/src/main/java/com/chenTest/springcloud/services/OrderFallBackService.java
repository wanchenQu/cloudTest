package com.chenTest.springcloud.services;

import org.springframework.stereotype.Component;

@Component
public class OrderFallBackService implements OrderHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "**********OrderFallBackService paymentInfo_OK fall back";
    }

    @Override
    public String paymentInfo_Timeout(Integer id) {
        return "**********OrderFallBackService paymentInfo_Timeout fall back";
    }
}
