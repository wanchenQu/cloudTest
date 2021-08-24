package com.chenTest.springcloud.handler;


import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.chenTest.springcloud.entities.CommonResult;

/**
 * 自定义限流处理器， 自定义异常提示
 */
public class CustomerBlockHandler {
    public static CommonResult handlerException1(BlockException e) {
        return new CommonResult(44441, "GLOBAL EXCEPTION HANDLER 1");
    }

    public static CommonResult handlerException2(BlockException e) {
        return new CommonResult(44442, "GLOBAL EXCEPTION HANDLER 2");
    }
}
