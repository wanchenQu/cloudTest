package com.chenTest.springcloud.service.impl;

import com.chenTest.springcloud.dao.StorageDao;
import com.chenTest.springcloud.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class StorageServiceImpl implements StorageService {

    @Resource
    private StorageDao storageDao;

    @Override
    public void decrease(Long productId, Integer count) {
        log.info("-------->storage-service中扣减库存");
        storageDao.decrease(productId, count);
    }
}
