package com.chenTest.springcloud.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.chenTest.springcloud.dao"})
public class MyBatisConfig {
}
