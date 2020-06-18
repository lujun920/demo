/*
 * Dian.so Inc.
 * Copyright (c) 2016-2019 All Rights Reserved.
 */
package so.dian.demo.redisson;

import org.junit.Test;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RLock;
import org.redisson.api.RQueue;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import so.dian.demo.BaseTest;
import so.dian.mofa3.cache.service.CacheService;
import so.dian.mofa3.cache.service.SerialNumService;

import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author ${baizhang}
 * @version $Id: RedissonTest.java, v 0.1 2019-02-18 7:00 PM Exp $
 */
public class RedissonTest extends BaseTest {
    @Autowired
    SerialNumService serialNumService;

    @Autowired
    CacheService cacheService;

    @Autowired
    RedissonClient redissonClient;

    @Test
    public void redissonFunction(){
        System.out.println("redisson set get delete。。。。");
        cacheService.set("AAAA", "ABCDEFRG");
        String getCache= cacheService.get("AAAA");
        System.out.println("get cache: "+ getCache);

        cacheService.delete("AAAA");

        System.out.println("流水号测试。。。。");
        System.out.println(serialNumService.getSerialNum("301"));
        System.out.println(serialNumService.getShortSerialNum("301"));
        System.out.println(serialNumService.listSerialNum("302", 5));
        System.out.println(serialNumService.listShortSerialNum("302", 5));

        System.out.println("锁测试。。。。");
        RLock lock = redissonClient.getLock("anyLock");
        // 最常见的使用方法
        lock.lock();
        lock.unlock();
        RLock fairLock = redissonClient.getFairLock("anyLock");
        // 最常见的使用方法
        fairLock.lock();
        fairLock.unlock();

        RReadWriteLock rwlock = redissonClient.getReadWriteLock("anyRWLock");
        // 最常见的使用方法
        rwlock.readLock().lock();
        rwlock.readLock().unlock();
        rwlock.writeLock().lock();
        rwlock.writeLock().unlock();

        System.out.println("队列测试。。。。");
        RQueue<String> queue = redissonClient.getQueue("anyQueue");
        queue.add("1111");
        String obj = queue.peek();
        String someObj = queue.poll();

        System.out.println("延迟队列测试。。。。");
        RQueue<String> queueDelay = redissonClient.getQueue("CACHE_WECHAT_MSG_QUEUE");
        RDelayedQueue<String> delayedQueue = redissonClient.getDelayedQueue(queueDelay);
        delayedQueue.offer("CCCCC", 10, TimeUnit.MINUTES);

//        delayedQueue.poll();
    }
}